package minecraft;

public class Bounds {

	private Position lower;
	private Position higher;

	public Bounds(Position lower, Position higher) {
		this.lower = lower;
		this.higher = higher;
	}

	public Position getLower() {
		return this.lower;
	}

	public Bounds setLower(Position lower) {
		this.lower = lower;
		return this;
	}

	public Position getHigher() {
		return this.higher;
	}

	public Bounds setHigher(Position higher) {
		this.higher = higher;
		return this;
	}
}
