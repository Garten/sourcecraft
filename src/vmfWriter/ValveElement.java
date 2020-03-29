package vmfWriter;

import java.io.IOException;

public abstract class ValveElement {

	public static final String ID_TAG = "id";
	public static final String COLOR = "color";
	public static final String VISGROUPSHOWN = "visgroupshown";
	public static final String EDITOR_TAG = "editor";
	public static final String VISGROUPAUTOSHOWN = "visgroupautoshown";
	public static final String ENTITY_TAG = "entity";
	public static final String CLASSNAME_TAG = "classname";

	public abstract void writeVmf(ValveWriter writer) throws IOException;
}
