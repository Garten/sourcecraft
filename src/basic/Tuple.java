package basic;

public class Tuple<A, B> {

	private A first;
	private B second;

	public Tuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public A getFirst() {
		return this.first;
	}

	public B getSecond() {
		return this.second;
	}
}
