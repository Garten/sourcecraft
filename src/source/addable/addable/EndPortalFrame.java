package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.addable.ConvertAction;

/**
 *
 *
 */
public class EndPortalFrame extends ConvertAction {

	public EndPortalFrame() {

	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		int parts = 4;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 1, 0);
		context.addDetail(context.createCuboid(p, end, parts, offset, negativeOffset, material));
		context.markAsConverted(p, end);
	}

}
