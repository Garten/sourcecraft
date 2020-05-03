package minecraft;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import minecraft.reader.nbt.McaReader;
import minecraft.reader.nbt.NamedTag;
import minecraft.reader.nbt.NbtTag;
import minecraft.reader.nbt.NbtTasks;

public class McaBlock {

	private static final NamedTag PALETTE_NAME = new NamedTag(NbtTag.STRING, "Name");
	private static final NamedTag PALETTE_PROPERTIES = new NamedTag(NbtTag.COMPOUND, "Properties");

	private String title;
	private Map<String, String> propertries = new HashMap<String, String>();

	public McaBlock setName(String title) {
		this.title = title;
		return this;
	}

	public String getName() {
		return this.title;
	}

	public McaBlock addProperty(McaBlockProperty property, String value) {
		this.propertries.put(property.toString(), value);
		return this;
	}

	public McaBlock addProperty(String property, String value) {
		this.propertries.put(property, value);
		return this;
	}

	public Map<String, String> getProperties() {
		return this.propertries;
	}

	public void read(McaReader mcaReader) throws IOException {
		mcaReader.doCompound(NbtTasks.I.create()
				.put(PALETTE_NAME, () -> this.setName(mcaReader.readString()))
				.put(PALETTE_PROPERTIES,
						() -> mcaReader.doCompond(title -> this.addProperty(title, mcaReader.readString()))));
	}

	public String getProperty(McaBlockProperty property) {
		return this.propertries.get(property.toString());
	}
}
