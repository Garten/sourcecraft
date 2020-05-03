package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class InfoPlayerTeamSpawn extends RotateablePointEntity {

	private int tf2Team = 0;

	public InfoPlayerTeamSpawn setTeamNumber(int teamNumber) {
		this.tf2Team = teamNumber;
		return this;
	}

	@Override
	public InfoPlayerTeamSpawn create(Position origin) {
		InfoPlayerTeamSpawn result = new InfoPlayerTeamSpawn();
		result.setRotation(this.getRotation());
		result.setTeamNum(this.getTeamNum());
		result.setOrigin(origin);
		return result;
	}

	public int getTeamNum() {
		return this.tf2Team;
	}

	public InfoPlayerTeamSpawn setTeamNum(int teamNum) {
		this.tf2Team = teamNum;
		return this;
	}

	@Override
	public String getName() {
		return "info_player_teamspawn";
	}

	@Override
	public void writeVmfSpecific2(ValveWriter writer) throws IOException {
//		writer.put("angles", "0 " + this.getRotation() + " 0")
		writer.put("StartDisabled", 0)
				.put("TeamNum", this.tf2Team);
	}
}
