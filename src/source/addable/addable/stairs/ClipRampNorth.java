package source.addable.addable.stairs;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.Orientation;
import vmfWriter.Ramp;

/**
 *
 *
 */
public class ClipRampNorth extends Addable {

	public ClipRampNorth() {
		int[] temp = { Material._STAIRS_NORTH, };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int materia) {

		Position end = this.cuboidFinder.getBestX(p, this.getMaterialUsedFor());
		Position offset = new Position(0, 0, 1);
		Position negativeOffset = new Position(0, 0, -1);
		Orientation cut1 = null;
		if (this.map.hasOrHadMaterial(p.getOffset(-1, 0, 0), Material._STAIRS_SMALL_EAST_NORTH)) {
			offset.move(-1, 0, 0);
		} else if (this.map.hasOrHadMaterial(p.getOffset(-1, 0, 0), Material._STAIRS_BIG_WEST_NORTH)) {
			offset.move(-1, 0, 0);
		}
		Orientation cut2 = null;
		if (this.map.hasOrHadMaterial(end.getOffset(1, 0, 0), Material._STAIRS_SMALL_WEST_NORTH)) {
			negativeOffset.move(-1, 0, 0);
		} else if (this.map.hasOrHadMaterial(end.getOffset(1, 0, 0), Material._STAIRS_BIG_EAST_NORTH)) {
			negativeOffset.move(-1, 0, 0);
		}
		// map.addRampCuttet( p, end, 2, offset, negativeOffset,
		// Material.PLAYER_CLIP, cut1, cut2, Orientation.NORTH );
		Ramp ramp = this.map.createRamp(this.map.createCuboid(p, end, 2, offset, negativeOffset, Material._PLAYER_CLIP), Orientation.NORTH);
		ramp.cut(cut1);
		ramp.cut(cut2);
		this.map.addSolid(ramp);
		this.map.markAsConverted(p, end);
	}
}
