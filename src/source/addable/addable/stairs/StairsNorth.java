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
public class StairsNorth extends Stairs {

	public StairsNorth() {
		int[] temp = { Material.COBBLESTONE_STAIRS_NORTH, Material.BRICK_STAIRS_NORTH, Material.STONE_BRICK_STAIRS_NORTH, Material.NETHER_BRICK_STAIRS_NORTH,
				Material.SANDSTONE_STAIRS_NORTH };
		super.setMaterialUsedFor(temp);
	}

	public StairsNorth(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsNorth(Material.COBBLESTONE_STAIRS_NORTH, Material.COBBLESTONE_SLAB));
		list.add(new StairsNorth(Material.BRICK_STAIRS_NORTH, Material.BRICK_SLAB));
		list.add(new StairsNorth(Material.STONE_BRICK_STAIRS_NORTH, Material.STONE_BRICK_SLAB));
		list.add(new StairsNorth(Material.SANDSTONE_STAIRS_NORTH, Material.SANDSTONE_SLAB));
		list.add(new ClipRampNorth());
		return list;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.NORTH;
	}

	@Override
	public void getType(Position p) {
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), this.getMaterialUsedFor())) {
			this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), new StairsEast().getMaterialUsedFor())) { // negative
																												// corner
				this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
				this.setSubBlock(SubBlockPosition.TOP_EAST_SOUTH);
			} else if (!this.map.hasOrHadMaterial(new Position(x, y, z - 1), new StairsWest().getMaterialUsedFor())) { // no
																														// corner
				this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), this.getMaterialUsedFor())) {
			this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), new StairsWest().getMaterialUsedFor())) { // negative
																												// corner
				this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
				this.setSubBlock(SubBlockPosition.TOP_WEST_SOUTH);
			} else if (!this.map.hasOrHadMaterial(new Position(x, y, z - 1), new StairsEast().getMaterialUsedFor())) { // no
																														// corner
				this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
			}
		}
		this.setSubBlock(SubBlockPosition.BOTTOM_EAST_SOUTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_EAST_NORTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_WEST_SOUTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_WEST_NORTH);
	}
}
