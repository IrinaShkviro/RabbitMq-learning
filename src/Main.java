import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
	public static void main(String[] args) {
		final String queueName = "Test";
		final String host = "localhost";

		Receiver printingReceiver = new PrintingReceiver(queueName, host);
		try {
			printingReceiver.start();
		} catch (TimeoutException | IOException exception) {
			System.out.println("Error occurred when tried to start receiver. " + exception.getMessage());
		}

		Sender randomSender = new RandomSender(queueName, host);
		try {
			randomSender.send(2);
		} catch (TimeoutException | IOException exception) {
			System.out.println("Error occurred when tried to send message. " + exception.getMessage());
		}
	}
}
