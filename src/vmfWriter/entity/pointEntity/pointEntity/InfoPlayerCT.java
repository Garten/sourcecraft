package vmfWriter.entity.pointEntity.pointEntity;

import minecraft.Position;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class InfoPlayerCT extends RotateablePointEntity {

	@Override
	public InfoPlayerCT create(Position origin) {
		InfoPlayerCT result = new InfoPlayerCT();
		result.setRotation(this.getRotation());
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "info_player_counterterrorist";
	}

//	@Override
//	public void writeVmfSpecific(ValveWriter writer) throws IOException {
//		writer.put("angles", "0 " + this.getRotation() + " 0");
//	}
}
