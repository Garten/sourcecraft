package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import minecraft.Property;

public class Trapdoor extends Action {
	private Mapper context;

	@Override
	public void add(Mapper context, Position position, Block block) {
		this.context = context;
		Position end = context.getCuboidFinder()
				.getBestY(position, block);
		String open = block.getProperty(Property.open);
		if (open.equals("true")) {
			this.handleOpen(position, end, block);
		} else {
			this.handleClosed(position, end, block);
		}
		context.markAsConverted(position, end);
	}

	private void handleOpen(Position position, Position end, Block block) {
		Position startOffset, endOffset;
		String dir = block.getProperty(Property.facing);
		if (dir.equals("north")) {
			startOffset = new Position(0, 0, 13);
			endOffset = new Position(0, 0, 0);
		} else if (dir.equals("south")) {
			startOffset = new Position(0, 0, 0);
			endOffset = new Position(0, 0, 13);
		} else if (dir.equals("east")) {
			startOffset = new Position(0, 0, 0);
			endOffset = new Position(13, 0, 0);
		} else {
			startOffset = new Position(13, 0, 0);
			endOffset = new Position(0, 0, 0);
		}
		int pixels = 16;
		context.addDetail(context.createCuboid(position, end, pixels, startOffset, endOffset, block));
	}

	private void handleClosed(Position position, Position end, Block block) {
		Position startOffset, endOffset;
		String side = block.getProperty(Property.half);
		if (side.equals("bottom")) {
			startOffset = new Position(0, 0, 0);
			endOffset = new Position(0, 13, 0);
		} else {
			startOffset = new Position(0, 13, 0);
			endOffset = new Position(0, 0, 0);
		}
		int pixels = 16;
		context.addDetail(context.createCuboid(position, end, pixels, startOffset, endOffset, block));
	}
}