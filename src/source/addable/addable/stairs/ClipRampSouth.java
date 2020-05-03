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
public class ClipRampSouth extends Addable {

	public ClipRampSouth() {
		int[] temp = { Material._STAIRS_SOUTH, };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int materia) {

		Position pMark = new Position(p);
		Position end = this.cuboidFinder.getBestX(p, this.getMaterialUsedFor());
		Position endMark = new Position(end);
		Position offset = new Position(0, 0, -1);
		Position negativeOffset = new Position(0, 0, 1);
		Orientation cut1 = null;
		if (this.map.hasOrHadMaterial(p.getOffset(-1, 0, 0), Material._STAIRS_SMALL_EAST_SOUTH)) {
			offset.move(-1, 0, 0);
			pMark.move(1, 0, 0);
		} else if (this.map.hasOrHadMaterial(p.getOffset(-1, 0, 0), Material._STAIRS_BIG_WEST_SOUTH)) {
			offset.move(-1, 0, 0);
			pMark.move(1, 0, 0);
		}
		Orientation cut2 = null;
		if (this.map.hasOrHadMaterial(end.getOffset(1, 0, 0), Material._STAIRS_SMALL_WEST_SOUTH)) {
			negativeOffset.move(-1, 0, 0);
			endMark.move(-1, 0, 0);
		} else if (this.map.hasOrHadMaterial(end.getOffset(1, 0, 0), Material._STAIRS_BIG_EAST_SOUTH)) {
			negativeOffset.move(-1, 0, 0);
			endMark.move(-1, 0, 0);
		}
		// System.out.println( "at p "+map.getMaterial( p.x-1, end.y, end.z
		// )+" at end "+map.getMaterial( end.x+1, end.y, end.z ) );

		// map.addRampCuttet( p, end, 2, offset, negativeOffset,
		// Material.PLAYER_CLIP, cut1, cut2, Orientation.SOUTH );
		Ramp ramp = this.map.createRamp(this.map.createCuboid(p, end, 2, offset, negativeOffset, Material._PLAYER_CLIP),
				Orientation.SOUTH);
		ramp.cut(cut1);
		ramp.cut(cut2);
		this.map.addSolid(ramp);
		this.map.markAsConverted(p, end);
	}
}
