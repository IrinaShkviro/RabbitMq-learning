import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class Sender {
	private final String QUEUE_NAME;
	private final ConnectionFactory factory;

	public Sender(final String queueName, final String host) {
		QUEUE_NAME = queueName;
		factory = new ConnectionFactory();
		factory.setHost(host);
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
		try (Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			for (int i = 0; i < nMessages; i++) {
				String message = getMessage();
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			}
		}
	}

	abstract String getMessage();
}