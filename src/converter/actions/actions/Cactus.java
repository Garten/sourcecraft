package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;

public class Cactus extends Action {

	public Cactus() {
		int[] temp = { MaterialLegacy.CACTUS };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position position, Block block) {
		Position end = context.getCuboidFinder()
				.getBestY(position, block);
		int parts = 8;
		Position offset = new Position(1, 0, 1);
		Position negativeOffset = new Position(1, 1, 1);
		context.addDetail(context.createCuboid(position, end, parts, offset, negativeOffset, block));
		context.markAsConverted(position, end);
	}
}
