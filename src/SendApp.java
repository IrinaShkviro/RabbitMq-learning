import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class SendApp {
    public static void main(String[] args) {
        final String queueName = "Test";
        final String host = "localhost";

        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             Sender randomSender = new RandomSender(queueName, host)) {

            System.out.println("In this program 'q' - exit, any other key(s) - continue");
            while (true) {
                randomSender.send();
                String line = input.readLine();
                if (line.length() == 1 && line.charAt(0) == 'q') {
                    break;
                }
            }
        } catch (TimeoutException | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
