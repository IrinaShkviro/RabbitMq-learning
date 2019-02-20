import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Receiver is a class to get messages from specified queue on specified host and process them
 * The handle function is identified by abstract function getHandler()
 */
public abstract class Receiver extends QueueWorker{
	Receiver(final String queueName, final String host) {
		super(queueName, host);
	}

	/**
	 * Create connection, channel, declare queue to get messages from, and handler function
	 * @throws IOException
	 * @throws TimeoutException
	 */
	void start() throws IOException, TimeoutException {
		if (channel != null && channel.isOpen()) {
			throw new RuntimeException("Connection was already opened");
		}
		connection = connectionFactory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			try {
				getHandler().handle(consumerTag, delivery);
			} finally {
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
	}

	abstract DeliverCallback getHandler();
}