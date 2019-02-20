import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Sender is a class to send messages to the specified queue on the specified host
 * The messages to send are identified by abstract function getMessage()
 */
public abstract class Sender extends QueueWorker {
	public Sender(final String queueName, final String host) {
		super(queueName, host);
	}

	public void send() throws IOException, TimeoutException {
		send(1);
	}

	/**
	 * Create connection, channel, declare queue to send messages.
	 * Send specified amount of messages into declared queue.
	 * @param nMessages amount of messages that sender will send to the queue
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public void send(int nMessages) throws TimeoutException, IOException {
		if (channel == null) {
			connection = connectionFactory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		}

		for (int i = 0; i < nMessages; i++) {
			String message = getMessage();
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		}
	}

	abstract String getMessage();
}