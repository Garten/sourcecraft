package vmfWriter;

public abstract class Solid extends ValveElement {

	protected static final String SOLID_TAG = "solid";

	protected Skin skin;

	public final void setSkin(Skin skin) {
		this.skin = skin;
	}
}
