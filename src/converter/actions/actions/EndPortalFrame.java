package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;

/**
 *
 *
 */
public class EndPortalFrame extends Action {

	public EndPortalFrame() {

	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		int parts = 4;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 1, 0);
		context.addDetail(context.createCuboid(p, end, parts, offset, negativeOffset, material));
		context.markAsConverted(p, end);
	}

}
