package vmfWriter.entity.pointEntity.pointEntity;

import minecraft.Position;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class InfoPlayerStart extends RotateablePointEntity {

	@Override
	public InfoPlayerStart create(Position origin) {
		InfoPlayerStart result = new InfoPlayerStart();
		result.setRotation(this.getRotation());
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "info_player_start";
	}

}
