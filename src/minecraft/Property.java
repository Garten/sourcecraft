package minecraft;

import basic.NameSupplier;

public enum Property implements NameSupplier {
	east,
	half,
	facing,
	north,
	open,
	shape,
	south,
	type,
	waterlogged,
	west;
	
	public enum East implements NameSupplier {
		true$,
		false$;
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
	
	public enum North implements NameSupplier {
		true$,
		false$;
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
	
	public enum South implements NameSupplier {
		true$,
		false$;
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
	
	public enum West implements NameSupplier {
		true$,
		false$;
	}
}