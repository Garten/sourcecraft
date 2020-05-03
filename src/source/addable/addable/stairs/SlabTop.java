package source.addable.addable.stairs;

import minecraft.Position;
import minecraft.SubBlockPosition;
import source.Material;
import source.addable.Addable;

public class SlabTop extends Addable {

	public SlabTop() {
		int[] temp = { Material.OAK_SLAB$TOP, Material.COBBLESTONE_SLAB$TOP, Material.BRICK_SLAB$TOP,
				Material.ANDESITE_SLAB$TOP, Material.NETHER_BRICK_SLAB$TOP, Material.ACACIA_SLAB$TOP,
				Material.BIRCH_SLAB$TOP, Material.DIORITE_SLAB$TOP, Material.CUT_RED_SANDSTONE_SLAB$TOP,
				Material.CUT_SANDSTONE_SLAB$TOP, Material.DIORITE_SLAB$TOP, Material.DARK_OAK_SLAB$TOP,
				Material.DARK_PRISMARINE_SLAB$TOP, Material.END_STONE_BRICK_SLAB$TOP, Material.GRANITE_SLAB$TOP,
				Material.JUNGLE_SLAB$TOP, Material.MOSSY_COBBLESTONE_SLAB$TOP, Material.MOSSY_STONE_BRICK_SLAB$TOP,
				Material.PETRIFIED_OAK_SLAB$TOP, Material.POLISHED_ANDESITE_SLAB$TOP,
				Material.POLISHED_DIORITE_SLAB$TOP, Material.POLISHED_GRANITE_SLAB$TOP,
				Material.PRISMARINE_BRICK_SLAB$TOP, Material.PURPUR_SLAB$TOP, Material.QUARTZ_SLAB$TOP,
				Material.RED_NETHER_BRICK_SLAB$TOP, Material.SANDSTONE_SLAB$TOP, Material.SMOOTH_QUARTZ_SLAB$TOP,
				Material.SMOOTH_RED_SANDSTONE_SLAB$TOP, Material.SMOOTH_SANDSTONE_SLAB$TOP,
				Material.SMOOTH_STONE_SLAB$TOP, Material.SPRUCE_SLAB$TOP, Material.STONE_BRICK_SLAB$TOP,
				Material.STONE_SLAB$TOP };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(Position position, int material) {
		this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
		this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
		this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
		this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
		this.map.markAsConverted(position);
	}

}
