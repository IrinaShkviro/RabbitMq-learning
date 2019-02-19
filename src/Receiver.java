import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class Receiver {
	private final String QUEUE_NAME;
	private final ConnectionFactory connectionFactory;

	Receiver(final String queueName, final String host) {
		QUEUE_NAME = queueName;
		connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(host);
	}

	void start() throws IOException, TimeoutException {
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicConsume(QUEUE_NAME, true, getDeliverCallback(), consumerTag -> { });
	}

	abstract DeliverCallback getDeliverCallback();
}