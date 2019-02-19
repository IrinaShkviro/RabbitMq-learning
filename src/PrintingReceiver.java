import com.rabbitmq.client.DeliverCallback;

public class PrintingReceiver extends Receiver {
	public PrintingReceiver(final String queueName, final String host) {
		super(queueName, host);
	}

	/**
	 *
	 * @return function that prints received message to console
	 */
	@Override
	DeliverCallback getDeliverCallback() {
		return (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("Received message: '" + message + "'");
		};
	}
}
