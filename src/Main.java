import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
	public static void main(String[] args) {
		final String queueName = "Test";
		final String host = "localhost";

		try (Receiver printingReceiver = new PrintingReceiver(queueName, host)) {
			printingReceiver.start();
			Sender randomSender = new RandomSender(queueName, host);
			randomSender.send(3);
		} catch (TimeoutException | IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
