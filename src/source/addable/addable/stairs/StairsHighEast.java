package source.addable.addable.stairs;

import java.util.LinkedList;

import minecraft.Position;
import minecraft.SubBlockPosition;
import source.Material;
import source.addable.Addable;
import vmfWriter.Orientation;

/**
 *
 *
 */
public class StairsHighEast extends Addable {

	private int materialReplacement;

	public StairsHighEast() {
		int[] temp = { Material.OAK_STAIRS_HIGH_EAST, Material.COBBLESTONE_STAIRS_HIGH_EAST, Material.BRICK_STAIRS_HIGH_EAST,
				Material.STONE_BRICK_STAIRS_HIGH_EAST, Material.NETHER_BRICK_STAIRS_HIGH_EAST, Material.SANDSTONE_STAIRS_HIGH_EAST,
				Material.SPRUCE_STAIRS_HIGH_EAST };
		super.setMaterialUsedFor(temp);
	}

	public StairsHighEast(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsHighEast(Material.OAK_STAIRS_HIGH_EAST, Material.OAK_SLAB));
		list.add(new StairsHighEast(Material.COBBLESTONE_STAIRS_HIGH_EAST, Material.COBBLESTONE_SLAB));
		list.add(new StairsHighEast(Material.BRICK_STAIRS_HIGH_EAST, Material.BRICK_SLAB));
		list.add(new StairsHighEast(Material.STONE_BRICK_STAIRS_HIGH_EAST, Material.STONE_BRICK_SLAB));
		list.add(new StairsHighEast(Material.NETHER_BRICK_STAIRS_HIGH_EAST, Material.NETHER_BRICK_SLAB));
		list.add(new StairsHighEast(Material.SPRUCE_STAIRS_HIGH_EAST, Material.SPRUCE_SLAB));
		list.add(new StairsHighEast(Material.BIRCH_STAIRS_HIGH_EAST, Material.BIRCH_SLAB));
		list.add(new StairsHighEast(Material.SANDSTONE_STAIRS_HIGH_EAST, Material.SANDSTONE_SLAB));
		return list;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.EAST;
	}

	@Override
	public void add(Position p, int material) {
		this.map.markAsConverted(p);
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), this.getMaterialUsedFor())) {
			this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsHighSouth().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsHighNorth().getMaterialUsedFor())) {
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x, y, z - 1), this.getMaterialUsedFor())) {
			this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsHighNorth().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsHighSouth().getMaterialUsedFor())) {
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
			}
		}
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_NORTH, this.materialReplacement);
	}
}
