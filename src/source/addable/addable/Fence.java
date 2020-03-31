package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.MaterialWallFilter;
import source.addable.Addable;
import vmfWriter.Orientation;

public class Fence extends Addable {

	private static int BEAM_SIDE = 7;
	private static int BEAM_TOP_OFF = 1;
	private static int BEAM_TOP_ON = 12;
	private static int BEAM_MID_OFF = 8;
	private static int BEAM_MID_ON = 5;

	public Fence() {
		int[] temp = { Material.OAK_FENCE, Material.NETHER_BRICK_FENCE };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestY(p, material);
		// pole
		int parts = 8;
		Position offset = new Position(3, 0, 3);
		Position negativeOffset = new Position(3, 0, 3);
		this.map.addDetail(this.map.createCuboid(p, end, parts, offset, negativeOffset, material));
		this.map.markAsConverted(p, end);
		// add beams
		parts = 16;
		while (p.getY() <= end.getY()) {
			if (this.map.hasMaterial(p.getOffset(1, 0, 0), material)) {
				offset = new Position(10, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
				offset = new Position(10, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
			} else if (this.map.hasOrHadMaterial(p.getOffset(1, 0, 0), new MaterialWallFilter(this.manager, Orientation.EAST))) {
				offset = new Position(10, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(parts, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
				offset = new Position(10, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(parts, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
			}

			if (this.map.hasOrHadMaterial(p.getOffset(-1, 0, 0), new MaterialWallFilter(this.manager, Orientation.WEST))) {
				offset = new Position(parts, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
				this.map.addDetail(this.map.createCuboid(p.getOffset(-1, 0, 0), p, parts, offset, negativeOffset, material));
				offset = new Position(parts, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
				this.map.addDetail(this.map.createCuboid(p.getOffset(-1, 0, 0), p, parts, offset, negativeOffset, material));
			}

			if (this.map.hasMaterial(p.getOffset(0, 0, 1), material)) {
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, 10);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, 10);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
			} else if (this.map.hasOrHadMaterial(p.getOffset(0, 0, 1), new MaterialWallFilter(this.manager, Orientation.SOUTH))) {
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, parts);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, parts);
				this.map.addDetail(this.map.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
			}

			if (this.map.hasOrHadMaterial(p.getOffset(0, 0, -1), new MaterialWallFilter(this.manager, Orientation.NORTH))) {
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, parts);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, 10);
				this.map.addDetail(this.map.createCuboid(p.getOffset(0, 0, -1), p, parts, offset, negativeOffset, material));
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, parts);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, 10);
				this.map.addDetail(this.map.createCuboid(p.getOffset(0, 0, -1), p, parts, offset, negativeOffset, material));
			}

			p.move(0, 1, 0);
			// Block.getMaterialUsedForStatic()
		}

	}
}
