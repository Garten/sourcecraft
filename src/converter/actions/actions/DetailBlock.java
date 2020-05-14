package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;
import vmfWriter.entity.solidEntity.FuncDetail;

/**
 * creates a "func_detail"-Block
 */
public class DetailBlock extends Action {

	public DetailBlock() {
		int[] temp = { MaterialLegacy.OAK_LEAVES, MaterialLegacy.GLASS, MaterialLegacy.ICE,
				MaterialLegacy.JUNGLE_LEAVES, MaterialLegacy.SPRUCE_LEAVES, MaterialLegacy.BIRCH_LEAVES,
				MaterialLegacy.ACACIA_LEAVES, MaterialLegacy.DARK_OAK_LEAVES };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(p, material);
		context.addSolidEntity(new FuncDetail(context.createCuboid(p, end, material)));
		context.markAsConverted(p, end);
	}
}
