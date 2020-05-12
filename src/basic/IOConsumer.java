package basic;

import java.io.IOException;

public interface IOConsumer<Argument> {

	public static final IOConsumer<String> INSTANCE = argument -> {
		// do nothing
	};

	public abstract void run(Argument argument) throws IOException;
}
