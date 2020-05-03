package source.addable.addable.stairs;

import minecraft.Position;
import minecraft.SubBlockPosition;
import source.Material;
import source.addable.Addable;

public class SlabBottom extends Addable {

	public SlabBottom() {
		int[] temp = { Material.OAK_SLAB$BOTTOM, Material.COBBLESTONE_SLAB$BOTTOM, Material.BRICK_SLAB$BOTTOM,
				Material.ANDESITE_SLAB$BOTTOM, Material.NETHER_BRICK_SLAB$BOTTOM, Material.ACACIA_SLAB$BOTTOM,
				Material.BIRCH_SLAB$BOTTOM, Material.DIORITE_SLAB$BOTTOM, Material.CUT_RED_SANDSTONE_SLAB$BOTTOM,
				Material.CUT_SANDSTONE_SLAB$BOTTOM, Material.DIORITE_SLAB$BOTTOM, Material.DARK_OAK_SLAB$BOTTOM,
				Material.DARK_PRISMARINE_SLAB$BOTTOM, Material.END_STONE_BRICK_SLAB$BOTTOM,
				Material.GRANITE_SLAB$BOTTOM, Material.JUNGLE_SLAB$BOTTOM, Material.MOSSY_COBBLESTONE_SLAB$BOTTOM,
				Material.MOSSY_STONE_BRICK_SLAB$BOTTOM, Material.PETRIFIED_OAK_SLAB$BOTTOM,
				Material.POLISHED_ANDESITE_SLAB$BOTTOM, Material.POLISHED_DIORITE_SLAB$BOTTOM,
				Material.POLISHED_GRANITE_SLAB$BOTTOM, Material.PRISMARINE_BRICK_SLAB$BOTTOM,
				Material.PURPUR_SLAB$BOTTOM, Material.QUARTZ_SLAB$BOTTOM, Material.RED_NETHER_BRICK_SLAB$BOTTOM,
				Material.SANDSTONE_SLAB$BOTTOM, Material.SMOOTH_QUARTZ_SLAB$BOTTOM,
				Material.SMOOTH_RED_SANDSTONE_SLAB$BOTTOM, Material.SMOOTH_SANDSTONE_SLAB$BOTTOM,
				Material.SMOOTH_STONE_SLAB$BOTTOM, Material.SPRUCE_SLAB$BOTTOM, Material.STONE_BRICK_SLAB$BOTTOM,
				Material.STONE_SLAB$BOTTOM };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(Position position, int material) {
		this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
		this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
		this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
		this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
		this.map.markAsConverted(position);
	}

}
