package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.SubBlockPosition;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;

public class SlabBottom extends ConvertAction {

	public SlabBottom() {
		int[] temp = { MaterialLegacy.OAK_SLAB$BOTTOM, MaterialLegacy.COBBLESTONE_SLAB$BOTTOM,
				MaterialLegacy.BRICK_SLAB$BOTTOM, MaterialLegacy.ANDESITE_SLAB$BOTTOM,
				MaterialLegacy.NETHER_BRICK_SLAB$BOTTOM, MaterialLegacy.ACACIA_SLAB$BOTTOM,
				MaterialLegacy.BIRCH_SLAB$BOTTOM, MaterialLegacy.DIORITE_SLAB$BOTTOM,
				MaterialLegacy.CUT_RED_SANDSTONE_SLAB$BOTTOM, MaterialLegacy.CUT_SANDSTONE_SLAB$BOTTOM,
				MaterialLegacy.DIORITE_SLAB$BOTTOM, MaterialLegacy.DARK_OAK_SLAB$BOTTOM,
				MaterialLegacy.DARK_PRISMARINE_SLAB$BOTTOM, MaterialLegacy.END_STONE_BRICK_SLAB$BOTTOM,
				MaterialLegacy.GRANITE_SLAB$BOTTOM, MaterialLegacy.JUNGLE_SLAB$BOTTOM,
				MaterialLegacy.MOSSY_COBBLESTONE_SLAB$BOTTOM, MaterialLegacy.MOSSY_STONE_BRICK_SLAB$BOTTOM,
				MaterialLegacy.PETRIFIED_OAK_SLAB$BOTTOM, MaterialLegacy.POLISHED_ANDESITE_SLAB$BOTTOM,
				MaterialLegacy.POLISHED_DIORITE_SLAB$BOTTOM, MaterialLegacy.POLISHED_GRANITE_SLAB$BOTTOM,
				MaterialLegacy.PRISMARINE_BRICK_SLAB$BOTTOM, MaterialLegacy.PURPUR_SLAB$BOTTOM,
				MaterialLegacy.QUARTZ_SLAB$BOTTOM, MaterialLegacy.RED_NETHER_BRICK_SLAB$BOTTOM,
				MaterialLegacy.SANDSTONE_SLAB$BOTTOM, MaterialLegacy.SMOOTH_QUARTZ_SLAB$BOTTOM,
				MaterialLegacy.SMOOTH_RED_SANDSTONE_SLAB$BOTTOM, MaterialLegacy.SMOOTH_SANDSTONE_SLAB$BOTTOM,
				MaterialLegacy.SMOOTH_STONE_SLAB$BOTTOM, MaterialLegacy.SPRUCE_SLAB$BOTTOM,
				MaterialLegacy.STONE_BRICK_SLAB$BOTTOM, MaterialLegacy.STONE_SLAB$BOTTOM };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(ConverterContext context, Position position, Block material) {
		context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
		context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
		context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
		context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
		context.markAsConverted(position);
	}

}
