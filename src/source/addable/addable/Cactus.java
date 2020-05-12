package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;

public class Cactus extends ConvertAction {

	public Cactus() {
		int[] temp = { MaterialLegacy.CACTUS };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position position, Block block) {
		Position end = context.getCuboidFinder()
				.getBestY(position, block);
		int parts = 8;
		Position offset = new Position(1, 0, 1);
		Position negativeOffset = new Position(1, 1, 1);
		context.addDetail(context.createCuboid(position, end, parts, offset, negativeOffset, block));
		context.markAsConverted(position, end);
	}
}
