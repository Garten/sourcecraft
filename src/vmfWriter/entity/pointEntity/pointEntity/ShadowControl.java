package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.Color;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;

public class ShadowControl extends PointEntity {

	private Color shadow;

	public ShadowControl(Position origin, Color shadowNew) {
		super(origin);
		this.shadow = new Color(shadowNew);
	}

	@Override
	public ShadowControl create(Position origin) {
		return new ShadowControl(origin, this.shadow);
	}

	@Override
	public String getName() {
		return "shadow_control";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("angles", "-70 356 0")
				.put("color", this.shadow)
				.put("disableallshadows", false)
				.put("distance", 75);
	}
}
