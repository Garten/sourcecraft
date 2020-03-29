package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntityRotateable;

public class InfoPlayerCT extends PointEntityRotateable {

	public InfoPlayerCT(int rotation) {
		super(rotation);
	}

	@Override
	public InfoPlayerCT create(Position origin) {
		InfoPlayerCT result = new InfoPlayerCT(this.getRotation());
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "info_player_counterterrorist";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("angles", "0 " + this.getRotation() + " 0");
	}
}
