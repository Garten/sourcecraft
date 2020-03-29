package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntityRotateable;

public class InfoPlayerTeamSpawn extends PointEntityRotateable {

	private int tf2Team;

	public InfoPlayerTeamSpawn() {
		super(0);
		// this.setOrigin(origin);
	}

	public InfoPlayerTeamSpawn(int rotation, int teamNumber) {
		super(rotation);
		this.tf2Team = teamNumber;
	}

	@Override
	public InfoPlayerTeamSpawn create(Position origin) {
		InfoPlayerTeamSpawn result = new InfoPlayerTeamSpawn(this.getRotation(), this.tf2Team);
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "info_player_teamspawn";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("angles", "0 " + this.getRotation() + " 0")
				.put("StartDisabled", 0)
				.put("TeamNum", this.tf2Team);
	}
}
