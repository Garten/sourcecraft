package basic;

public class Value<T> {

	private T value;

	public Value<T> set(T value) {
		this.value = value;
		return this;
	}

	public T get() {
		return this.value;
	}
}
