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
public class ClipRampWest extends Addable {

	public ClipRampWest() {
		int[] temp = { Material._STAIRS_WEST, Material._STAIRS_BIG_WEST_NORTH, Material._STAIRS_BIG_WEST_SOUTH,
				Material._STAIRS_SMALL_WEST_NORTH, Material._STAIRS_SMALL_WEST_SOUTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {

		Position end = this.cuboidFinder.getBestZ(p, this.getMaterialUsedFor());
		Position offset = new Position(1, 0, 0);
		Position negativeOffset = new Position(-1, 0, 0);
		Orientation cut1 = null;
		if (this.map.getMaterial(new Position(p)) == Material._STAIRS_SMALL_WEST_SOUTH) {
			cut1 = Orientation.SOUTH;
			offset.move(0, 0, -1);
		}
		Orientation cut2 = null;
		if (this.map.getMaterial(new Position(end)) == Material._STAIRS_SMALL_WEST_NORTH) {
			cut2 = Orientation.NORTH;
			negativeOffset.move(0, 0, -1);
		}
		// map.addRampCuttet( p, end, 2, offset, negativeOffset,
		// Material.PLAYER_CLIP, cut1, cut2, Orientation.WEST );
		Ramp ramp = this.map.createRamp(this.map.createCuboid(p, end, 2, offset, negativeOffset, Material._PLAYER_CLIP),
				Orientation.WEST);
		ramp.cut(cut1);
		ramp.cut(cut2);
		this.map.addSolid(ramp);

		// add unseen south/norht ramps
		if (this.map.hasOrHadMaterial(p, Material._STAIRS_SMALL_WEST_SOUTH)) {
			Position neighbour = p.getOffset(-1, 0, 0);
			int[] materialsStairs = { Material._STAIRS_SMALL_EAST_SOUTH, Material._STAIRS_BIG_WEST_SOUTH };
			if (this.map.hasOrHadMaterial(neighbour, materialsStairs)) {
				offset = new Position(1, 0, -1);
				negativeOffset = new Position(-1, 0, 1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.SOUTH);
				this.map.addSolid(this.map.createRamp(
						this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP),
						Orientation.SOUTH));
			} else if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_SOUTH) == false) {
				offset = new Position(2, 0, -1);
				negativeOffset = new Position(-1, 0, 1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.SOUTH);
				this.map.addSolid(this.map.createRamp(
						this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP),
						Orientation.SOUTH));
			}
		}
		if (this.map.hasOrHadMaterial(end, Material._STAIRS_SMALL_WEST_NORTH)) {
			Position neighbour = end.getOffset(-1, 0, 0);
			int[] materials = { Material._STAIRS_SMALL_EAST_NORTH, Material._STAIRS_BIG_WEST_NORTH };
			if (this.map.hasOrHadMaterial(neighbour, materials)) {
				offset = new Position(1, 0, 1);
				negativeOffset = new Position(-1, 0, -1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.NORTH);
				this.map.addSolid(this.map.createRamp(
						this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP),
						Orientation.NORTH));
			} else if (this.map.hasOrHadMaterial(neighbour, Material._STAIRS_NORTH) == false) {
				offset = new Position(2, 0, 1);
				negativeOffset = new Position(-1, 0, -1);
				// map.addRamp( neighbour, neighbour, 2, offset, negativeOffset,
				// Material.PLAYER_CLIP, Orientation.NORTH);
				this.map.addSolid(this.map.createRamp(
						this.map.createCuboid(neighbour, neighbour, 2, offset, negativeOffset, Material._PLAYER_CLIP),
						Orientation.NORTH));
			}
		}

		this.map.markAsConverted(p, end);
	}
}
