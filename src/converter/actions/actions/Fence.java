package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import minecraft.Property;
import vmfWriter.Orientation;

public class Fence extends Action {

	private static int BEAM_SIDE = 7;
	private static int BEAM_TOP_OFF = 1;
	private static int BEAM_TOP_ON = 12;
	private static int BEAM_MID_OFF = 8;
	private static int BEAM_MID_ON = 5;
	private Mapper context;

	@Override
	public boolean hasWall(Orientation orientation) {
		return true;
	}

	@Override
	public void add(Mapper context, Position p, Block block) {
		this.context = context;
		Position end = context.getCuboidFinder()
				.getBestY(p, block);
		Position startOffset = new Position(3, 0, 3);
		Position endOffset = new Position(3, 0, 3);
		this.addPole(p, end, block, startOffset, endOffset);
		this.addBeams(p, end, block, startOffset, endOffset);
		this.context.markAsConverted(p, end);
	}
	
	private void addPole(Position p, Position end, Block block, Position startOffset, Position endOffset) {
		int parts = 8;
		this.context.addDetail(context.createCuboid(p, end, parts, startOffset, endOffset, block));
	}

	private void addBeams(Position p, Position end, Block block, Position startOffset, Position endOffset) {
		boolean north = block.getProperty(Property.north).equals("true");
		boolean south = block.getProperty(Property.south).equals("true");
		boolean east = block.getProperty(Property.east).equals("true");
		boolean west = block.getProperty(Property.west).equals("true");
		int parts = 16;
		while (p.getY() <= end.getY()) {
			if (east && west) {
				this.addEWBeams(p, startOffset, endOffset, parts, block);
			} else if (east) {
				this.addEastBeams(p, startOffset, endOffset, parts, block);
			} else if (west) {
				this.addWestBeams(p, startOffset, endOffset, parts, block);
			}

			if (north && south) {
				this.addNSBeams(p, startOffset, endOffset, parts, block);
			} else if (south) {
				this.addSouthBeams(p, startOffset, endOffset, parts, block);
			} else if (north) {
				this.addNorthBeams(p, startOffset, endOffset, parts, block);
			}
			p.move(0, 1, 0);
		}
	}

	private void addEWBeams(Position p, Position startOffset, Position endOffset, int parts, Block block) {
		startOffset = new Position(0, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
		endOffset = new Position(parts, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
		context.addDetail(
			context.createCuboid(p, p.getOffset(1, 0, 0), parts, startOffset, endOffset, block));
		startOffset = new Position(0, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
		endOffset = new Position(parts, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
		context.addDetail(
			context.createCuboid(p, p.getOffset(1, 0, 0), parts, startOffset, endOffset, block));
	}

	private void addNSBeams(Position p, Position startOffset, Position endOffset, int parts, Block block) {
		startOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, parts);
		endOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, 0);
		context.addDetail(
			context.createCuboid(p.getOffset(0, 0, -1), p, parts, startOffset, endOffset, block));
		startOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, parts);
		endOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, 0);
		context.addDetail(
				context.createCuboid(p.getOffset(0, 0, -1), p, parts, startOffset, endOffset, block));
	}

	private void addEastBeams(Position p, Position startOffset, Position endOffset, int parts, Block block) {
		startOffset = new Position(10, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
		endOffset = new Position(parts, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
		context.addDetail(
			context.createCuboid(p, p.getOffset(1, 0, 0), parts, startOffset, endOffset, block));
		startOffset = new Position(10, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
		endOffset = new Position(parts, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
		context.addDetail(
			context.createCuboid(p, p.getOffset(1, 0, 0), parts, startOffset, endOffset, block));
	}

	private void addWestBeams(Position p, Position startOffset, Position endOffset, int parts, Block block) {
		startOffset = new Position(parts, Fence.BEAM_TOP_ON, Fence.BEAM_SIDE);
		endOffset = new Position(10, Fence.BEAM_TOP_OFF, Fence.BEAM_SIDE);
		context.addDetail(
			context.createCuboid(p.getOffset(-1, 0, 0), p, parts, startOffset, endOffset, block));
		startOffset = new Position(parts, Fence.BEAM_MID_ON, Fence.BEAM_SIDE);
		endOffset = new Position(10, Fence.BEAM_MID_OFF, Fence.BEAM_SIDE);
		context.addDetail(
			context.createCuboid(p.getOffset(-1, 0, 0), p, parts, startOffset, endOffset, block));
	}

	private void addSouthBeams(Position p, Position startOffset, Position endOffset, int parts, Block block) {
		startOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, 10);
		endOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, parts);
		context.addDetail(
			context.createCuboid(p, p.getOffset(0, 0, 1), parts, startOffset, endOffset, block));
		startOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, 10);
		endOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, parts);
		context.addDetail(
			context.createCuboid(p, p.getOffset(0, 0, 1), parts, startOffset, endOffset, block));
	}

	private void addNorthBeams(Position p, Position startOffset, Position endOffset, int parts, Block block) {
		startOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_ON, parts);
		endOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_TOP_OFF, 10);
		context.addDetail(
			context.createCuboid(p.getOffset(0, 0, -1), p, parts, startOffset, endOffset, block));
		startOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_ON, parts);
		endOffset = new Position(Fence.BEAM_SIDE, Fence.BEAM_MID_OFF, 10);
		context.addDetail(
				context.createCuboid(p.getOffset(0, 0, -1), p, parts, startOffset, endOffset, block));
	}
}
