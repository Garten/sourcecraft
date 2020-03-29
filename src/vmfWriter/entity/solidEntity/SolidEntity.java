package vmfWriter.entity.solidEntity;

import java.io.IOException;

import vmfWriter.Solid;
import vmfWriter.ValveWriter;
import vmfWriter.entity.Entity;

public abstract class SolidEntity extends Entity {

	protected Solid solids[];

	public SolidEntity(Solid solid) {
		this.solids = new Solid[1];
		this.solids[0] = solid;
	}

	public SolidEntity(Solid[] solids) {
		this.solids = solids;
	}

	public void writeSolids(ValveWriter writer) throws IOException {
		for (Solid solid : this.solids) {
			solid.writeVmf(writer);
		}
	}

	@Override
	protected void writeEnd(ValveWriter writer) throws IOException {
		this.writeSolids(writer);
		super.writeEnd(writer);
	}
}
