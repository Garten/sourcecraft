package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;

public class SnowBlock extends ConvertAction {

	public SnowBlock() {
		int[] temp = { MaterialLegacy.SNOW };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		int parts = 8;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 7, 0);
		context.addDetail(context.createCuboid(p, end, parts, offset, negativeOffset, material));
		context.markAsConverted(p, end);
	}
}
