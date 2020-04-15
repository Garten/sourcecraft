package source.addable.addable.tf2;

import java.util.LinkedList;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.Tf2Team;
import vmfWriter.entity.pointEntity.RotateablePointEntity;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerTeamSpawn;
import vmfWriter.entity.solidEntity.Buyzone;

public class PlayerSpawnTf2 extends Addable {

	private final static int SPACE = 50;

	private RotateablePointEntity type;
	private boolean police;

	public PlayerSpawnTf2() {

	}

	public PlayerSpawnTf2(int material, RotateablePointEntity type, boolean police) {
		super.setMaterialUsedFor(material);
		this.type = type;
		this.police = police;
	}

	@Override
	public Iterable<Addable> getInstances() {
		RotateablePointEntity redSpawn = new InfoPlayerTeamSpawn().setTeamNum(Tf2Team.RED)
				.setRotation(0);
		RotateablePointEntity blueSpawn = new InfoPlayerTeamSpawn().setTeamNum(Tf2Team.BLUE)
				.setRotation(180);
		LinkedList<Addable> list = new LinkedList<>();
		list.add(new PlayerSpawnTf2(Material.END_PORTAL_FRAME, redSpawn, false));
		list.add(new PlayerSpawnTf2(Material.ENDER_CHEST_NORTH, blueSpawn, true));
		list.add(new PlayerSpawnTf2(Material.ENDER_CHEST_EAST, blueSpawn, true));
		list.add(new PlayerSpawnTf2(Material.ENDER_CHEST_SOUTH, blueSpawn, true));
		list.add(new PlayerSpawnTf2(Material.ENDER_CHEST_WEST, blueSpawn, true));
		return list;
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXZ(p, material);
		this.map.addPointEntitys(p, end, PlayerSpawnTf2.SPACE, this.type);
		this.map.markAsConverted(p, end);
		end.move(0, 2, 0);
		this.map.addSolidEntity(new Buyzone(this.map.createCuboid(p, end, 0), this.police));
	}
}
