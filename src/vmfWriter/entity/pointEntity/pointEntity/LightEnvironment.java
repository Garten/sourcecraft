package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.Color;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;

public class LightEnvironment extends PointEntity {

	private Color light;
	private Color ambient;

	public LightEnvironment(Position origin, Color light, Color ambient) {
		super(origin);
		this.light = light.copy();
		this.ambient = ambient.copy();
	}

	@Override
	public LightEnvironment create(Position origin) {
		return new LightEnvironment(origin, this.light.copy(), this.ambient.copy());
	}

	@Override
	public String getName() {
		return "light_environment";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		// writer.put("_ambient", angles)
		writer.putTransparentColor("_ambient", this.ambient)
				.put("_ambientHDR", "-1 -1 -1 1")
				.put("_AmbientScaleHDR", 1)
				.put("angles", "-70 356 0")
				.put("pitch", -70)
				.put("SunSpreadAngle", 0);
	}
}
