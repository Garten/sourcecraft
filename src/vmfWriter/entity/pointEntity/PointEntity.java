package vmfWriter.entity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.Entity;

public abstract class PointEntity extends Entity {

	protected Position origin;

	public PointEntity() {
		this.origin = new Position();
	}

	public PointEntity(Position origin) {
		this.origin = Position.create(origin);
	}

	protected void setOrigin(Position origin) {
		this.origin = Position.create(origin);
	}

	public abstract PointEntity create(Position origin);

	@Override
	protected void writeEnd(ValveWriter writer) throws IOException {
		writer.put(Entity.ORIGIN_TAG, this.origin.getString());
		super.writeEnd(writer);
	}
}
