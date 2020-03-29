package basic;

public class Loggger {

	private static final int TAB_SIZE = 8;
	private static final int DEFAULT_INDENT = 1 * TAB_SIZE;
	private static int minimalIndent = DEFAULT_INDENT;

	public static void debug(String message) {
		print(message, 0);
	}

	public static void log(String message) {
		print(message, 0);
	}

	public static void logg(String message) {
		print(message, 1);
	}

	public static void log(String message, int callDepth) {

	}

	public static void warn(String message) {
		print(message, 0);
	}

	public static void error(String message) {
		print(message, 0);
	}

	public static void maybeLog(String message) {
		if (Math.random() < 0.1f) {
			print(message, 0);
		}
	}

	public static void print(String mesasge, int callDepth) {
		StringBuilder stringBuilder = new StringBuilder();
		addCallLocation(stringBuilder, callDepth);
		adjustMinimalIndent(stringBuilder);
		addTabs(stringBuilder);
		stringBuilder.append(mesasge);
		System.out.println(stringBuilder.toString());
	}

	private static final int EXPECTED_STACK_DEPTH = 4;

	private static void addCallLocation(StringBuilder stringBuilder, int additonalCallDepth) {
		int callDepth = EXPECTED_STACK_DEPTH + additonalCallDepth;
		StackTraceElement[] elements = Thread.currentThread()
				.getStackTrace();
		if (elements.length >= callDepth) {
			StackTraceElement element = elements[callDepth];
			String text = "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
			stringBuilder.append(text);
		}
	}

	private static void adjustMinimalIndent(StringBuilder stringBuilder) {
		while (minimalIndent < stringBuilder.length() + 1) {
			minimalIndent += TAB_SIZE;
		}
	}

	private static void addTabs(StringBuilder stringBuilder) {
		int blankLength = minimalIndent - stringBuilder.length();
		while (blankLength > 0) {
			blankLength -= TAB_SIZE;
			stringBuilder.append("\t");
		}
	}
}
