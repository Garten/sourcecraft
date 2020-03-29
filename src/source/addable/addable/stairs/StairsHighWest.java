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
public class StairsHighWest extends Addable {

	private int materialReplacement;

	public StairsHighWest() {
		int[] temp = { Material.COBBLESTONE_STAIRS_HIGH_WEST, Material.BRICK_STAIRS_HIGH_WEST, Material.STONE_BRICK_STAIRS_HIGH_WEST,
				Material.NETHER_BRICK_STAIRS_HIGH_WEST, Material.SANDSTONE_STAIRS_HIGH_WEST, };
		super.setMaterialUsedFor(temp);
	}

	public StairsHighWest(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsHighWest(Material.COBBLESTONE_STAIRS_HIGH_WEST, Material.COBBLESTONE_SLAB));
		list.add(new StairsHighWest(Material.BRICK_STAIRS_HIGH_WEST, Material.BRICK_SLAB));
		list.add(new StairsHighWest(Material.STONE_BRICK_STAIRS_HIGH_WEST, Material.STONE_BRICK_SLAB));
		list.add(new StairsHighWest(Material.SANDSTONE_STAIRS_HIGH_WEST, Material.SANDSTONE_SLAB));
		return list;
	}

	@Override
	public String getName() {
		return "stairsHighWest";
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.WEST;
	}

	@Override
	public void add(Position p, int material) {
		this.map.markAsConverted(p);
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), this.getMaterialUsedFor())) {
			this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsHighSouth().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(new Position(x, y, z), SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsHighNorth().getMaterialUsedFor())) {
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x, y, z - 1), this.getMaterialUsedFor())) {
			this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsHighNorth().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsHighSouth().getMaterialUsedFor())) {
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
			}
		}
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_NORTH, this.materialReplacement);
	}
}
