package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;

/**
 * A non-solid block (thus counts as air) that has a lowered height when it ends
 *
 */
public class Liquid extends ConvertAction {

	public Liquid() {
		super.materialUsedFor = new int[] { MaterialLegacy.WATER, MaterialLegacy.LAVA, MaterialLegacy.SEAGRASS,
				MaterialLegacy.TALL_SEAGRASS, MaterialLegacy.KELP, MaterialLegacy.KELP_PLANT };
	}

	@Override
	public void add(ConverterContext context, Position position, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(position, material);
		context.addSolid(context.createCuboid(position, end, material));
		context.markAsConverted(position, end);
	}
}
