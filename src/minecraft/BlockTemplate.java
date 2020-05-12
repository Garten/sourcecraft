package minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BlockTemplate extends Block {

	private String title;
	private Map<String, String> properties;

	public BlockTemplate() {
	}

	public BlockTemplate setName(Material material) {
		this.title = material.getName();
		return this;
	}

	public BlockTemplate setName(String title) {
		this.title = title;
		return this;
	}

	public BlockTemplate setProperties(Map<String, String> properties) {
		this.properties = properties;
		return this;
	}

	public BlockTemplate addProperty(Supplier<String> property, Supplier<String> value) {
		if (this.properties == null) {
			this.properties = new HashMap<String, String>();
		}
		this.properties.put(property.get(), value.get());
		return this;
	}

	public BlockTemplate addProperty(String property, String value) {
		if (this.properties == null) {
			this.properties = new HashMap<String, String>();
		}
		assert property != null;
		this.properties.put(property, value);
		return this;
	}

	public BlockTemplate copyFrom(Block template) {
		this.title = template.getName();
		this.properties = template.getProperties();
		return this;
	}

	public BlockTemplate reset() {
		this.title = null;
		this.properties = null;
		return this;
	}

	@Override
	public String getName() {
		return this.title;
	}

	@Override
	public Map<String, String> getProperties() {
		return this.properties;
	}
}
