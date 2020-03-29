package vmfWriter.texture;

import java.awt.Color;
import java.io.IOException;

import vmfWriter.ValveElement;
import vmfWriter.ValveWriter;

public class ValveTexture extends ValveElement {

	private static final String COLOR = "$color";
	private static final String TRANSLUCENT = "$translucent";
	private static final String SURFACE_PROP = "$surfaceprop";
	private static final String BASE_TEXTURE = "$basetexture";
	private String baseTexture;
	private String surfaceProp = "brick";
	private boolean translucent;
	private Color color;

	public ValveTexture(String baseTexture) {
		this.baseTexture = baseTexture;
		this.translucent = false;
		this.color = null;
	}

	public ValveTexture setTranslucent(boolean translucent) {
		this.translucent = translucent;
		return this;
	}

	public ValveTexture setColor(Color color) {
		this.color = color;
		return this;
	}

	@Override
	public void writeVmf(ValveWriter writer) throws IOException {
		writer.open("\"LightmappedGeneric\"")
				.put(BASE_TEXTURE, this.baseTexture)
				.put(SURFACE_PROP, this.surfaceProp)
				.put(TRANSLUCENT, this.translucent)
				.putInBrackets(COLOR, this.color)
				.close();
	}

}
