package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.Color;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.NamedPointEntity;

public class PropDynamic extends NamedPointEntity {

	private String model;
	private int angleX = 0;
	private int angleY = 0;
	private int angleZ = 0;

	public PropDynamic(String model, int angleX, int angleY, int angleZ) {
		this.model = model;
		this.angleX = angleX;
		this.angleY = angleY;
		this.angleZ = angleZ;
	}

	@Override
	public PropDynamic create(Position origin) {
		PropDynamic result = new PropDynamic(this.model, this.angleX, this.angleY, this.angleZ);
		result.setOrigin(origin);
		return (PropDynamic) super.createNamedPointEntity(result);
	}

	@Override
	public String getName() {
		return "prop_dynamic";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("angles", this.angleX + " " + this.angleY + " " + this.angleZ)
				.put("DisableBoneFollowers", false)
				.put("disablereceiveshadows", false)
				.put("disableshadows", false)
				.put("ExplodeDamage", 0)
				.put("ExplodeRadius", 0)
				.put("fademaxdist", 0)
				.put("fademindist", -1)
				.put("fadescale", 1)
				.put("MaxAnimTime", 10)
				.put("maxdxlevel", 0)
				.put("MinAnimTime", 5)
				.put("mindxlevel", 0)
				.put("model", this.model)
				.put("screenspacefade", 0)
				.put("PerformanceMode", 0)
				.put("pressuredelay", 0)
				.put("RandomAnimation", 0)
				.put("renderamt", 255)
				.put("rendercolor", Color.FULL)
				.put("renderfx", 0)
				.put("rendermode", 0)
				.put("SetBodyGroup", 0)
				.put("skin", 0)
				.put("solid", 0)
				.put("spawnflags", 0)
				.put("targetname", this.getTargetName());
	}

}
