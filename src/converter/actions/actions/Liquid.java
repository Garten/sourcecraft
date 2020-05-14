package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;

/**
 * A non-solid block (thus counts as air) that has a lowered height when it ends
 *
 */
public class Liquid extends Action {

	public Liquid() {
		super.materialUsedFor = new int[] { MaterialLegacy.WATER, MaterialLegacy.LAVA, MaterialLegacy.SEAGRASS,
				MaterialLegacy.TALL_SEAGRASS, MaterialLegacy.KELP, MaterialLegacy.KELP_PLANT };
	}

	@Override
	public void add(Mapper context, Position position, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(position, material);
		context.addSolid(context.createCuboid(position, end, material));
		context.markAsConverted(position, end);
	}
}
