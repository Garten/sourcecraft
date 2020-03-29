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
public class ClipRampEast extends Addable {

	public ClipRampEast() {
		int[] temp = { Material._STAIRS_EAST, Material._STAIRS_BIG_EAST_NORTH, Material._STAIRS_BIG_EAST_SOUTH, Material._STAIRS_SMALL_EAST_NORTH,
				Material._STAIRS_SMALL_EAST_SOUTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public String getName() {
		return "clipRampEast";
	}

	@Override
	public void add(Position p, int material) {

		Position end = this.cuboidFinder.getBestZ(p, this.getMaterialUsedFor());
		Position offset = new Position(-1, 0, 0);
		Position negativeOffset = new Position(1, 0, 0);
		Orientation cut1 = null;
		if (this.map.getMaterial(p) == Material._STAIRS_SMALL_EAST_SOUTH) {
			offset.move(0, 0, -1);
			cut1 = Orientation.SOUTH;
		}
		Orientation cut2 = null;
		if (this.map.getMaterial(p) == Material._STAIRS_SMALL_EAST_NORTH) {
			negativeOffset.move(0, 0, -1);
			cut2 = Orientation.NORTH;
		}
		// map.addRampCuttet( p, end, 2, offset, negativeOffset,
		// Material.PLAYER_CLIP, cut1, cut2, Orientation.EAST );
		Ramp ramp = this.map.createRamp(this.map.createCuboid(p, end, 2, offset, negativeOffset, Material._PLAYER_CLIP), Orientation.EAST);
		ramp.cut(cut1);
		ramp.cut(cut2);
		this.map.addSolid(ramp);

		// add unseen south/norht ramps
		if (this.map.hasOrHadMaterial(p, Material._STAIRS_SMALL_EAST_SOUTH)) {
			Position neighbour = p.getOffset(1, 0, 0);
			if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_BIG_EAST_SOUTH)) {
				offset = new Position(-1, 0, -1);
				negativeOffset = new Position(1, 0, 1);
				this.map.addSolid(
						this.map.createRamp(this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP), Orientation.SOUTH));
			} else if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_SMALL_WEST_SOUTH)) {
				// already covered by ClipRampWest
			} else if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_SOUTH) == false) {
				offset = new Position(-1, 0, -1);
				negativeOffset = new Position(2, 0, 1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.SOUTH);
				this.map.addSolid(
						this.map.createRamp(this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP), Orientation.SOUTH));
			}
		}
		if (this.map.hasOrHadMaterial(end, Material._STAIRS_SMALL_EAST_NORTH)) {
			Position neighbour = end.getOffset(1, 0, 0);
			if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_BIG_EAST_NORTH)) {
				offset = new Position(-1, 0, 1);
				negativeOffset = new Position(1, 0, -1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.NORTH);
				this.map.addSolid(
						this.map.createRamp(this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP), Orientation.NORTH));
			} else if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_SMALL_WEST_NORTH)) {
				// already covered by ClipRampWest
			} else if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_NORTH) == false) {
				offset = new Position(-1, 0, 1);
				negativeOffset = new Position(2, 0, -1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.NORTH);
				this.map.addSolid(
						this.map.createRamp(this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP), Orientation.NORTH));
			}
		}
		this.map.markAsConverted(p, end);
	}
}
