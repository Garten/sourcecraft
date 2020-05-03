package vmfWriter.entity;

import java.io.IOException;

import vmfWriter.ValveElement;
import vmfWriter.ValveWriter;

public abstract class Entity extends ValveElement {

	public static final String ENTITY_TAG = "entity";
	public static final String CLASSNAME_TAG = "classname";
	public static final String ORIGIN_TAG = "origin";
	public static final String LOGICALPOS_TAG = "logicalpos";

	public String name;

	@Override
	public void writeVmf(ValveWriter writer) throws IOException {
		this.writeStart(writer);
		this.writeVmfSpecific(writer);
		this.writeEnd(writer);
	}

	protected void writeStart(ValveWriter writer) throws IOException {
		this.openEntity(writer);
	}

	public void writeVmfSpecific(ValveWriter writer) throws IOException {

	}

	protected void writeEnd(ValveWriter writer) throws IOException {
		this.writeEditorWithLogicalpos(writer);
		writer.close();
	}

	public ValveWriter openEntity(ValveWriter writer) throws IOException {
		return writer.open(ValveElement.ENTITY_TAG)
				.put(ValveElement.ID_TAG, writer.getCounter()
						.getBrushId())
				.put(ValveElement.CLASSNAME_TAG, this.getName());
	}

	public String getName() {
		return this.name;
	}

	public Entity setName(String name) { // TODO some entities overwrite getName, for others the name is set from extern
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
