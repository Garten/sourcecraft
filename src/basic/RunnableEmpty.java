package basic;

public class RunnableEmpty implements Runnable {

	public static final RunnableEmpty INSTANCE = new RunnableEmpty();

	@Override
	public void run() {
		// do nothing
	}

	public static void doNothing() {
		// do nothing;
	}

}
