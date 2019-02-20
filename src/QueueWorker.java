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
    private final ConnectionFactory connectionFactory;
    private volatile Connection connection = null;
    protected Channel channel = null;

    public QueueWorker(final String queueName, final String host) {
        QUEUE_NAME = queueName;
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
    }

    /**
     * Checks if connection has opened and open it if it hasn't opened.
     * @return true if connection was opened, false otherwise
     * @throws TimeoutException
     * @throws IOException
     */
    protected boolean openConnectionIfNotExist() throws TimeoutException, IOException{
        if (connection != null && connection.isOpen()) {
            return false;
        }
        synchronized(this) {
            if (connection != null && connection.isOpen()) {
                return false;
            }
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            return true;
        }
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
