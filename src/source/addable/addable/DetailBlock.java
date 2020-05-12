package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;
import vmfWriter.entity.solidEntity.FuncDetail;

/**
 * creates a "func_detail"-Block
 */
public class DetailBlock extends ConvertAction {

	public DetailBlock() {
		int[] temp = { MaterialLegacy.OAK_LEAVES, MaterialLegacy.GLASS, MaterialLegacy.ICE,
				MaterialLegacy.JUNGLE_LEAVES, MaterialLegacy.SPRUCE_LEAVES, MaterialLegacy.BIRCH_LEAVES,
				MaterialLegacy.ACACIA_LEAVES, MaterialLegacy.DARK_OAK_LEAVES };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(p, material);
		context.addSolidEntity(new FuncDetail(context.createCuboid(p, end, material)));
		context.markAsConverted(p, end);
	}
}
