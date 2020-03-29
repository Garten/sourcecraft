package basic;

import periphery.Place;

public interface RunnableWith<Argument> {

	public static final RunnableWith<String> INSTANCE = argument -> {
		// do nothing
	};

	public static final RunnableWith<Place> INSTANCE_PLACE = argument -> {
		// do nothing
	};

	public abstract void run(Argument argument);
}
