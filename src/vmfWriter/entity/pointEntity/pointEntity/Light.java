package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.Color;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;

public class Light extends PointEntity {

	private Color color;
	private int distance50;
	private int distance100;

	public Light(Color color, int distance50, int distance100) {
		this.color = color;
		this.distance50 = distance50;
		this.distance100 = distance100;
	}

	@Override
	public Light create(Position origin) {
		Light result = new Light(this.color, this.distance50, this.distance100);
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "light";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("_constant_attn", 0)
				.put("_distance", 0)
				.put("_fifty_percent_distance", this.distance50)
				.put("_hardfalloff", 0)
				.put("_light",
						this.color.getRed() + " " + this.color.getGreen() + " " + this.color.getBlue() + " "
								+ this.color.getAlpha())
				.put("_lightHDR", "-1 -1 -1 1")
				.put("_lightscaleHDR", 1)
				.put("_linear_attn", 0)
				.put("_quadratic_attn", 1)
				.put("_zero_percent_distance", this.distance100)
				.put("style", 0);
	}
}
