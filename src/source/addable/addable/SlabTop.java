package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.SubBlockPosition;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;

public class SlabTop extends ConvertAction {

	public SlabTop() {
		int[] temp = { MaterialLegacy.OAK_SLAB$TOP, MaterialLegacy.COBBLESTONE_SLAB$TOP, MaterialLegacy.BRICK_SLAB$TOP,
				MaterialLegacy.ANDESITE_SLAB$TOP, MaterialLegacy.NETHER_BRICK_SLAB$TOP, MaterialLegacy.ACACIA_SLAB$TOP,
				MaterialLegacy.BIRCH_SLAB$TOP, MaterialLegacy.DIORITE_SLAB$TOP,
				MaterialLegacy.CUT_RED_SANDSTONE_SLAB$TOP, MaterialLegacy.CUT_SANDSTONE_SLAB$TOP,
				MaterialLegacy.DIORITE_SLAB$TOP, MaterialLegacy.DARK_OAK_SLAB$TOP,
				MaterialLegacy.DARK_PRISMARINE_SLAB$TOP, MaterialLegacy.END_STONE_BRICK_SLAB$TOP,
				MaterialLegacy.GRANITE_SLAB$TOP, MaterialLegacy.JUNGLE_SLAB$TOP,
				MaterialLegacy.MOSSY_COBBLESTONE_SLAB$TOP, MaterialLegacy.MOSSY_STONE_BRICK_SLAB$TOP,
				MaterialLegacy.PETRIFIED_OAK_SLAB$TOP, MaterialLegacy.POLISHED_ANDESITE_SLAB$TOP,
				MaterialLegacy.POLISHED_DIORITE_SLAB$TOP, MaterialLegacy.POLISHED_GRANITE_SLAB$TOP,
				MaterialLegacy.PRISMARINE_BRICK_SLAB$TOP, MaterialLegacy.PURPUR_SLAB$TOP,
				MaterialLegacy.QUARTZ_SLAB$TOP, MaterialLegacy.RED_NETHER_BRICK_SLAB$TOP,
				MaterialLegacy.SANDSTONE_SLAB$TOP, MaterialLegacy.SMOOTH_QUARTZ_SLAB$TOP,
				MaterialLegacy.SMOOTH_RED_SANDSTONE_SLAB$TOP, MaterialLegacy.SMOOTH_SANDSTONE_SLAB$TOP,
				MaterialLegacy.SMOOTH_STONE_SLAB$TOP, MaterialLegacy.SPRUCE_SLAB$TOP,
				MaterialLegacy.STONE_BRICK_SLAB$TOP, MaterialLegacy.STONE_SLAB$TOP };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(ConverterContext context, Position position, Block material) {
		context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
		context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
		context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
		context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
		context.markAsConverted(position);
	}

}
