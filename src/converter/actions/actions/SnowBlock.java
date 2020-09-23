package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;

public class SnowBlock extends Action {

	@Override
	public void add(Mapper context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		int parts = 8;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 7, 0);
		context.addDetail(context.createCuboid(p, end, parts, offset, negativeOffset, material));
		context.markAsConverted(p, end);
	}
}
