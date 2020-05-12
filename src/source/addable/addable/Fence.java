package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.MaterialWallFilter;
import source.addable.ConvertAction;
import vmfWriter.Orientation;

public class Fence extends ConvertAction {

	private static int BEAM_SIDE = 7;
	private static int BEAM_TOP_OFF = 1;
	private static int BEAM_TOP_ON = 12;
	private static int BEAM_MID_OFF = 8;
	private static int BEAM_MID_ON = 5;

	public Fence() {
		int[] temp = { MaterialLegacy.ACACIA_FENCE, MaterialLegacy.BIRCH_FENCE, MaterialLegacy.DARK_OAK_FENCE,
				MaterialLegacy.JUNGLE_FENCE, MaterialLegacy.NETHER_BRICK_FENCE, MaterialLegacy.OAK_FENCE,
				MaterialLegacy.SPRUCE_FENCE };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return true;
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestY(p, material);
		// pole
		int parts = 8;
		Position offset = new Position(3, 0, 3);
		Position negativeOffset = new Position(3, 0, 3);
		context.addDetail(context.createCuboid(p, end, parts, offset, negativeOffset, material));
		context.markAsConverted(p, end);
		// add beams
		parts = 16;
		while (p.getY() <= end.getY()) {
			if (context.hasMaterial(p.getOffset(1, 0, 0), material)) {
				offset = new Position(10, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
				context.addDetail(
						context.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
				offset = new Position(10, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
				context.addDetail(
						context.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
			} else if (context.hasOrHadMaterial(p.getOffset(1, 0, 0),
					new MaterialWallFilter(context.getActions(), Orientation.EAST))) {
				offset = new Position(10, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(parts, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
				context.addDetail(
						context.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
				offset = new Position(10, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(parts, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
				context.addDetail(
						context.createCuboid(p, p.getOffset(1, 0, 0), parts, offset, negativeOffset, material));
			}

			if (context.hasOrHadMaterial(p.getOffset(-1, 0, 0),
					new MaterialWallFilter(context.getActions(), Orientation.WEST))) {
				offset = new Position(parts, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
				context.addDetail(
						context.createCuboid(p.getOffset(-1, 0, 0), p, parts, offset, negativeOffset, material));
				offset = new Position(parts, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
				negativeOffset = new Position(10, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
				context.addDetail(
						context.createCuboid(p.getOffset(-1, 0, 0), p, parts, offset, negativeOffset, material));
			}

			if (context.hasMaterial(p.getOffset(0, 0, 1), material)) {
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, 10);
				context.addDetail(
						context.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, 10);
				context.addDetail(
						context.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
			} else if (context.hasOrHadMaterial(p.getOffset(0, 0, 1),
					new MaterialWallFilter(context.getActions(), Orientation.SOUTH))) {
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, parts);
				context.addDetail(
						context.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, 10);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, parts);
				context.addDetail(
						context.createCuboid(p, p.getOffset(0, 0, 1), parts, offset, negativeOffset, material));
			}

			if (context.hasOrHadMaterial(p.getOffset(0, 0, -1),
					new MaterialWallFilter(context.getActions(), Orientation.NORTH))) {
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, parts);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, 10);
				context.addDetail(
						context.createCuboid(p.getOffset(0, 0, -1), p, parts, offset, negativeOffset, material));
				offset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, parts);
				negativeOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, 10);
				context.addDetail(
						context.createCuboid(p.getOffset(0, 0, -1), p, parts, offset, negativeOffset, material));
			}

			p.move(0, 1, 0);
			// Block.getMaterialUsedForStatic()
		}

	}
}
