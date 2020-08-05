package vmfWriter.entity;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

import vmfWriter.ValveElement;
import vmfWriter.ValveWriter;
import vmfWriter.VmfValue;

public abstract class Entity extends ValveElement {

	public static final String ENTITY_TAG = "entity";
	public static final String CLASSNAME_TAG = "classname";
	public static final String ORIGIN_TAG = "origin";
	public static final String LOGICALPOS_TAG = "logicalpos";

	public String name;

	private HashMap<String, VmfValue> properties = new HashMap<>();

	@Override
	public void writeVmf(ValveWriter writer) throws IOException {
		this.writeStart(writer);
		this.writeProperties(writer);
		this.writeVmfSpecific(writer);
		this.writeEnd(writer);
	}

	protected void writeStart(ValveWriter writer) throws IOException {
		this.openEntity(writer);
	}

	public void writeVmfSpecific(ValveWriter writer) throws IOException {

	}

	public void writeProperties(ValveWriter writer) throws IOException {
		properties.forEach((key, value) -> {
			try {
				writer.put(key, value.getVmf());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}

	public void putProperty(String property, VmfValue value) {
		this.properties.put(property, value);
	}

	public VmfValue getProperty(String property) {
		return this.properties.get(property);
	}

	protected void writeEnd(ValveWriter writer) throws IOException {
		this.writeEditorWithLogicalpos(writer);
		writer.close();
	}

	public ValveWriter openEntity(ValveWriter writer) throws IOException {
		return writer.open(ValveElement.ENTITY_TAG)
				.put(ValveElement.ID_TAG, writer.getCounter()
						.getNewBrushId())
				.put(ValveElement.CLASSNAME_TAG, this.getName());
	}

	public String getName() {
		return this.name;
	}

	public Entity setName(String name) {
		this.name = name;
		return this;
	}

	public void writeEditorWithLogicalpos(ValveWriter writer) throws IOException {
		writer.open(ValveElement.EDITOR_TAG)
				.put(ValveElement.COLOR, "220 30 220")
				.put(ValveElement.VISGROUPSHOWN, 1)
				.put(ValveElement.VISGROUPAUTOSHOWN, 1)
				.put(Entity.LOGICALPOS_TAG, "[0 1000]")
				.close();
	}
}
