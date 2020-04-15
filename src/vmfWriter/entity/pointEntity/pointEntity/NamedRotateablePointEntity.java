package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class NamedRotateablePointEntity extends RotateablePointEntity {

	private String name;

	@Override
	public NamedRotateablePointEntity create(Position origin) {
		NamedRotateablePointEntity result = new NamedRotateablePointEntity();
		result.setName(this.getName());
		result.setRotation(this.getRotation());
		result.setOrigin(origin);
		return result;
	}

	public NamedRotateablePointEntity setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("angles", "0 " + this.getRotation() + " 0");
	}

}
