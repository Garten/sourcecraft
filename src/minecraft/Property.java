package minecraft;

import basic.NameSupplier;

public enum Property implements NameSupplier {
	half,
	facing,
	open,
	shape,
	type,
	waterlogged;
	
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

	public enum Open implements NameSupplier {
		true$,
		false$;
	}
	
	public enum Shape implements NameSupplier {
		inner_left,
		inner_right,
		outer_left,
		outer_right,
		straight
	}
	
	public enum Type implements NameSupplier {
		top,
		bottom,
		double$;
	}

	public enum Waterlogged implements NameSupplier {
		true$,
		false$;
	}
}