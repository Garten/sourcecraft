package source.addable.addable;

import minecraft.Position;
import source.addable.Addable;

/**
 *
 *
 */
public class EndPortalFrame extends Addable {

	public EndPortalFrame() {

	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXZ(p, material);
		int parts = 4;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 1, 0);
		this.map.addDetail(this.map.createCuboid(p, end, parts, offset, negativeOffset, material));
		this.map.markAsConverted(p, end);
	}

}
