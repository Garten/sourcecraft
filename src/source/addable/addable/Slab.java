package source.addable.addable;

import minecraft.Position;
import minecraft.SubBlockPosition;
import source.Material;
import source.addable.Addable;

public class Slab extends Addable {

	public Slab() {
		int[] temp = { Material.STONE_SLAB, Material.SANDSTONE_SLAB, Material.OAK_SLAB, Material.COBBLESTONE_SLAB, Material.BRICK_SLAB,
				Material.STONE_BRICK_SLAB, Material.ANDESITE_SLAB, Material.NETHER_BRICK_SLAB, Material.ACACIA_SLAB, Material.BIRCH_SLAB, Material.DIORITE_SLAB,
				Material.CUT_RED_SANDSTONE_SLAB, Material.CUT_SANDSTONE_SLAB, Material.DIORITE_SLAB }; // TODO
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
