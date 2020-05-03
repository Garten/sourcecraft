package source.addable.addable;

import basic.Loggger;
import minecraft.Position;
import source.Material;
import source.SkinManager;
import source.addable.Addable;
import vmfWriter.Orientation;

public class Block extends Addable {

	public Block() {
		// is default addable
	}

	@Override
	public boolean isAirBlock() {
		return false;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return true;
	}

	@Override
	public void add(Position position, int material) {
		Position end = this.cuboidFinder.getBestXYZ(position, material);
		if (SkinManager.INSTANCE.getSkin(material).materialFront.equals(SkinManager.DEFAULT_TEXTURE)) { // temp
			Loggger.log("no texture set for " + Material.getName(material));
		}
		this.map.addSolid(this.map.createCuboid(position, end, material));
		this.map.markAsConverted(position, end);
	}
}
