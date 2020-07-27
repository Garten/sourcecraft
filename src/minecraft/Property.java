package minecraft;

import basic.NameSupplier;

public enum Property implements NameSupplier {
	type,
	half,
	facing,
	shape,
	waterlogged;

	public enum Type implements NameSupplier {
		top,
		bottom,
		double$;
	}

	public enum Half implements NameSupplier {
		top,
		bottom,
		double$;
	}

	public enum Facing implements NameSupplier {
		north,
		east,
		south,
		west
	}

	public enum Shape implements NameSupplier {
		inner_left,
		inner_right,
		outer_left,
		outer_right,
		straight
	}

	public enum Waterlogged implements NameSupplier {
		true$,
		false$;
	}
}