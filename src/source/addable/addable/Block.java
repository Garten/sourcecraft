package source.addable.addable;

import minecraft.Position;
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
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXYZ(p, material);
		this.map.addSolid(this.map.createCuboid(p, end, material));
		this.map.markAsConverted(p, end);
	}
}
