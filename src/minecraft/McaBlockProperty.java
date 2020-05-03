package minecraft;

public enum McaBlockProperty {
	half,
	facing,
	shape;

	public enum Half {
		top,
		bottom;
	}

	public enum Facing {
		north,
		east,
		south,
		west
	}

	public enum Shape {
		inner_left,
		inner_right,
		outer_left,
		outer_right,
		straight
	}
}