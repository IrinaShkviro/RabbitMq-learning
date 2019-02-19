import com.rabbitmq.client.DeliverCallback;

/**
 * Printing receiver is a class to print messages from specified queue on specified host
 */
public class PrintingReceiver extends Receiver {
	public PrintingReceiver(final String queueName, final String host) {
		super(queueName, host);
	}

	/**
	 *
	 * @return function that prints received message to console
	 */
	@Override
	DeliverCallback getHandler() {
		return (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("Received message: '" + message + "'");
		};
	}
}
