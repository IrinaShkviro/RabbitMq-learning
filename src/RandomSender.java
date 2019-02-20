import java.util.Random;

/**
 * The RandomSender is a Sender to send random strings to the queue with 'queue_name' on host 'host'
 */
public class RandomSender extends Sender {
	private final Random RANDOM;
	private final int MAX_LENGTH = 256;
	private final int FIRST_CHAR = 'a'; // code of start symbol of random range (included)
	private final int LAST_CHAR = 'z'; // code of end symbol of the random range (included)

	public RandomSender(final String queueName, final String host) {
		super(queueName, host);
		RANDOM = new Random();
	}

	/**
	 *
	 * @return string with random symbols with random length in range [1, MAX_LENGTH]
	 */
	@Override
	String getMessage() {
		int length = 1 + RANDOM.nextInt(MAX_LENGTH);
		return getRandomString(length);
	}

	/**
	 *
	 * @param length - length of the returned random string
	 * @return string with 'length' random symbols, whose codes in range [FIRST_CHAR, LAST_CHAR]
	 */
	private String getRandomString(int length) {
		StringBuilder buffer = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomInt = FIRST_CHAR + RANDOM.nextInt(LAST_CHAR - FIRST_CHAR + 1);
			buffer.append((char) randomInt);
		}
		return buffer.toString();
	}
}
