package basic;

import periphery.Place;

public interface Validator<Argument> {

	public static final Validator<Place> PLACE_ACCEPTOR = argument -> true;

	public abstract boolean run(Argument argument);
}
