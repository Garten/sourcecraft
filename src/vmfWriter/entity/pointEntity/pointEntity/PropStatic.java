package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.Angles;
import vmfWriter.Counter;
import vmfWriter.entity.pointEntity.PointEntity;

public class PropStatic extends PointEntity {

	private String model;
	private Angles angles;

	public PropStatic(String model) {
		this(model, new Angles());
	}

	public PropStatic(String model, Angles angles) {
		this.model = model;
		this.angles = angles.copy();
	}

	public Angles getAngles() {
		return this.angles;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public PropStatic create(Position origin) {
		PropStatic result = new PropStatic(this.model, this.angles);
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "prop_static";
	}

	public void writeVmfSpecifc(Counter counter, vmfWriter.ValveWriter writer) throws IOException {
		writer.put("angles", this.angles)
				.put("disableselfshadowing", false)
				.put("disableshadows", false)
				.put("disablevertexlighting", false)
				.put("fademaxdist", 0)
				.put("fademindist", -1)
				.put("fadescale", 1)
				.put("ignorenormals", false)
				.put("maxdxlevel", 0)
				.put("mindxlevel", 0)
				.put("model", this.model)
				.put("screenspacefade", 0)
				.put("skin", 0)
				.put("solid", 0);
	}
}
