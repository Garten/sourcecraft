package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.Angles;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;

public class InfoParticleSystem extends PointEntity {

	private final String effectName;
	private final int angleX;
	private final int angleY;
	private final int angleZ;

	public InfoParticleSystem(String effectName, int angleX, int angleY, int angleZ) {
		this.effectName = effectName;
		this.angleX = angleX;
		this.angleY = angleY;
		this.angleZ = angleZ;
	}

	@Override
	public InfoParticleSystem create(Position origin) {
		InfoParticleSystem result = new InfoParticleSystem(this.effectName, this.angleX, this.angleY, this.angleZ);
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "info_particle_system";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		Angles angles = new Angles(this.angleX, this.angleY, this.angleZ);
		writer.put("angles", angles)
				.put("cpoint1_parent", 0)
				.put("cpoint2_parent", 0)
				.put("cpoint3_parent", 0)
				.put("cpoint4_parent", 0)
				.put("cpoint5_parent", 0)
				.put("cpoint6_parent", 0)
				.put("cpoint7_parent", 0)
				.put("effect_name", this.effectName)
				.put("flag_as_weather", false)
				.put("start_active", true);
	}
}
