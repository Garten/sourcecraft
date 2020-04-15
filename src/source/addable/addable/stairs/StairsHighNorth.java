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
public class StairsHighNorth extends Addable {

	private int materialReplacement;

	public StairsHighNorth() {
		int[] temp = { Material.COBBLESTONE_STAIRS_HIGH_NORTH, Material.BRICK_STAIRS_HIGH_NORTH, Material.STONE_BRICK_STAIRS_HIGH_NORTH,
				Material.NETHER_BRICK_STAIRS_HIGH_NORTH, Material.SANDSTONE_STAIRS_HIGH_NORTH, };
		super.setMaterialUsedFor(temp);
	}

	public StairsHighNorth(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsHighNorth(Material.OAK_STAIRS_HIGH_NORTH, Material.OAK_SLAB));
		list.add(new StairsHighNorth(Material.COBBLESTONE_STAIRS_HIGH_NORTH, Material.COBBLESTONE_SLAB));
		list.add(new StairsHighNorth(Material.BRICK_STAIRS_HIGH_NORTH, Material.BRICK_SLAB));
		list.add(new StairsHighNorth(Material.STONE_BRICK_STAIRS_HIGH_NORTH, Material.STONE_BRICK_SLAB));
		list.add(new StairsHighNorth(Material.SANDSTONE_STAIRS_HIGH_NORTH, Material.SANDSTONE_SLAB));
		return list;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.NORTH;
	}

	@Override
	public void add(Position p, int material) {
		this.map.markAsConverted(p);
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), this.getMaterialUsedFor())) {
			this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), new StairsHighEast().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x, y, z - 1), new StairsHighWest().getMaterialUsedFor())) { // no
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), this.getMaterialUsedFor())) {
			this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), new StairsHighWest().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x, y, z - 1), new StairsHighEast().getMaterialUsedFor())) { // no
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
			}
		}
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_NORTH, this.materialReplacement);
	}
}
