package basic;

import java.io.IOException;

public interface ThrowingRunnableWith<Argument> {

	public static final ThrowingRunnableWith<String> INSTANCE = argument -> {
		// do nothing
	};

	public abstract void run(Argument argument) throws IOException;
}
