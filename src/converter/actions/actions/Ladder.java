package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Position;
import minecraft.Property;

public class Ladder extends Action {

	private Mapper context;

	@Override
	public void add(Mapper context, Position pos, Block block) {
        this.context = context;
		Position end = context.getCuboidFinder()
				.getBestY(pos, block);
        this.handleLadderDirection(pos, end, block);
        this.handleClipDirection(pos, end, block);
		context.markAsConverted(pos, end);
	}

	private void handleLadderDirection(Position pos, Position end, Block block) {
		Position startOffset, endOffset;
        String dir = block.getProperty(Property.facing);
		int pixels = 16;
        if (dir.equals("north")) {
            startOffset = new Position(0, 0, 15);
            endOffset = new Position(0, 0, 0);
        } else if (dir.equals("south")) {
            startOffset = new Position(0, 0, 0);
            endOffset = new Position(0, 0, 15);
        } else if (dir.equals("east")) {
            startOffset = new Position(0, 0, 0);
            endOffset = new Position(15, 0, 0);
        } else {
            startOffset = new Position(15, 0, 0);
            endOffset = new Position(0, 0, 0);
        } 
		this.context.addDetail(context.createCuboid(pos, end, pixels, startOffset, endOffset, block));
    }
    
	private void handleClipDirection(Position pos, Position end, Block block) {
		Position startOffset, endOffset;
        String dir = block.getProperty(Property.facing);
		int pixels = 8;
        if (dir.equals("north")) {
            startOffset = new Position(1, 0, 7);
            endOffset = new Position(1, 0, 0);
        } else if (dir.equals("south")) {
            startOffset = new Position(1, 0, 0);
            endOffset = new Position(1, 0, 7);
        } else if (dir.equals("east")) {
            startOffset = new Position(0, 0, 1);
            endOffset = new Position(7, 0, 1);
        } else {
            startOffset = new Position(7, 0, 1);
            endOffset = new Position(0, 0, 1);
        } 
        this.context.addDetail(context.createCuboid(pos, end, pixels, startOffset, endOffset, 
            Blocks.get("sourcecraft:ladder")));
	}
}
