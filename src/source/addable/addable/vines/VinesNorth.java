package source.addable.addable.vines;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;
import vmfWriter.entity.solidEntity.FuncIllusionary;

/**
 *
 *
 */
public class VinesNorth extends ConvertAction {

	public VinesNorth() {
		int[] temp = { MaterialLegacy.VINES };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestY(p, material);
		int parts = 8;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 0, 7);
		context.addSolidEntity(
				new FuncIllusionary(context.createCuboid(p, end, parts, offset, negativeOffset, material)));
		context.markAsConverted(p, end);
	}
}
