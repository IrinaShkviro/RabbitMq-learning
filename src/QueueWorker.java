import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * QueueWorker is the main class for work with queues
 */
public class QueueWorker implements AutoCloseable {
    protected final String QUEUE_NAME;
    protected final ConnectionFactory connectionFactory;
    protected Connection connection = null;
    protected Channel channel = null;

    public QueueWorker(final String queueName, final String host) {
        QUEUE_NAME = queueName;
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
    }

    @Override
    public void close() throws IOException, TimeoutException {
        if (channel != null) {
            channel.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
