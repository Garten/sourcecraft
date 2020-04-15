package source.addable.addable;

import java.util.LinkedList;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.pointEntity.RotateablePointEntity;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerCT;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerT;
import vmfWriter.entity.solidEntity.Buyzone;

public class PlayerSpawnCss extends Addable {

	private final static int SPACE = 40;

	private RotateablePointEntity type;
	private boolean police;

	public PlayerSpawnCss() {

	}

	public PlayerSpawnCss(int material, RotateablePointEntity type, boolean police) {
		super.setMaterialUsedFor(material);
		this.type = type;
		this.police = police;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<>();
		list.add(new PlayerSpawnCss(Material.END_PORTAL_FRAME, new InfoPlayerT().setRotation(0), false));
		list.add(new PlayerSpawnCss(Material.ENDER_CHEST_NORTH, new InfoPlayerCT().setRotation(180), true));
		list.add(new PlayerSpawnCss(Material.ENDER_CHEST_EAST, new InfoPlayerCT().setRotation(180), true));
		list.add(new PlayerSpawnCss(Material.ENDER_CHEST_SOUTH, new InfoPlayerCT().setRotation(180), true));
		list.add(new PlayerSpawnCss(Material.ENDER_CHEST_WEST, new InfoPlayerCT().setRotation(180), true));
		return list;
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXZ(p, material);
		this.map.addPointEntitys(p, end, PlayerSpawnCss.SPACE, this.type);
		end.move(0, 2, 0);
		this.map.addSolidEntity(new Buyzone(this.map.createCuboid(p, end, 0), this.police));
		this.map.markAsConverted(p, end);
	}
}
