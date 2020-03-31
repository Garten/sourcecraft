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
public class StairsWest extends Stairs {

	public StairsWest() {
		int[] temp = { Material.OAK_STAIRS_WEST, Material.COBBLESTONE_STAIRS_WEST, Material.BRICK_STAIRS_WEST, Material.STONE_BRICK_STAIRS_WEST,
				Material.NETHER_BRICK_STAIRS_WEST, Material.SANDSTONE_STAIRS_WEST };
		super.setMaterialUsedFor(temp);
	}

	public StairsWest(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsWest(Material.OAK_STAIRS_WEST, Material.OAK_SLAB));
		list.add(new StairsWest(Material.COBBLESTONE_STAIRS_WEST, Material.COBBLESTONE_SLAB));
		list.add(new StairsWest(Material.BRICK_STAIRS_WEST, Material.BRICK_SLAB));
		list.add(new StairsWest(Material.STONE_BRICK_STAIRS_WEST, Material.STONE_BRICK_SLAB));
		list.add(new StairsWest(Material.SANDSTONE_STAIRS_WEST, Material.SANDSTONE_SLAB));
		list.add(new ClipRampWest());
		return list;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.WEST;
	}

	@Override
	public void getType(Position p) {
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), this.getMaterialUsedFor())) {
			this.setSubBlock(SubBlockPosition.TOP_WEST_SOUTH);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsSouth().getMaterialUsedFor())) { // negative
				// corner
				this.setSubBlock(SubBlockPosition.TOP_WEST_SOUTH);
				this.setSubBlock(SubBlockPosition.TOP_EAST_SOUTH);
			} else if (!this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsNorth().getMaterialUsedFor())) {
				this.setSubBlock(SubBlockPosition.TOP_WEST_SOUTH);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x, y, z - 1), this.getMaterialUsedFor())) {
			this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsNorth().getMaterialUsedFor())) { // negative
				// corner
				this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
				this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
			} else if (!this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsSouth().getMaterialUsedFor())) {
				this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
			}
		}
		this.setSubBlock(SubBlockPosition.BOTTOM_EAST_SOUTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_EAST_NORTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_WEST_SOUTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_WEST_NORTH);

	}
}
