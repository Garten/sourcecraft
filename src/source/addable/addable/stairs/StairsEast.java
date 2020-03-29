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
public class StairsEast extends Stairs {

	public StairsEast() {
		int[] temp = { Material.OAK_STAIRS_EAST, Material.COBBLESTONE_STAIRS_EAST, Material.BRICK_STAIRS_EAST, Material.STONE_BRICK_STAIRS_EAST,
				Material.NETHER_BRICK_STAIRS_EAST, Material.SANDSTONE_STAIRS_EAST };
		super.setMaterialUsedFor(temp);
	}

	public StairsEast(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsEast(Material.OAK_STAIRS_EAST, Material.OAK_SLAB));
		list.add(new StairsEast(Material.COBBLESTONE_STAIRS_EAST, Material.COBBLESTONE_SLAB));
		list.add(new StairsEast(Material.BRICK_STAIRS_EAST, Material.BRICK_SLAB));
		list.add(new StairsEast(Material.STONE_BRICK_STAIRS_EAST, Material.STONE_BRICK_SLAB));
		list.add(new StairsEast(Material.NETHER_BRICK_STAIRS_EAST, Material.NETHER_BRICK_SLAB));
		list.add(new StairsEast(Material.SANDSTONE_STAIRS_EAST, Material.SANDSTONE_SLAB));
		list.add(new ClipRampEast());
		return list;
	}

	@Override
	public String getName() {
		return "stairsEast";
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.EAST;
	}

	@Override
	protected final void getType(Position p) {
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();

		if (this.map.hasOrHadMaterial(new Position(x, y, z + 1), this.getMaterialUsedFor())) {
			this.setSubBlock(SubBlockPosition.TOP_EAST_SOUTH);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsSouth().getMaterialUsedFor())) { // negative
				// corner
				this.setSubBlock(SubBlockPosition.TOP_EAST_SOUTH);
				this.setSubBlock(SubBlockPosition.TOP_WEST_SOUTH);
			} else if (!this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsNorth().getMaterialUsedFor())) { // no
																														// corner
				this.setSubBlock(SubBlockPosition.TOP_EAST_SOUTH);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x, y, z - 1), this.getMaterialUsedFor())) {
			this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), new StairsNorth().getMaterialUsedFor())) { // negative
				// corner
				this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
				this.setSubBlock(SubBlockPosition.TOP_WEST_NORTH);
			} else if (!this.map.hasOrHadMaterial(new Position(x + 1, y, z), new StairsSouth().getMaterialUsedFor())) { // no
																														// corner
				this.setSubBlock(SubBlockPosition.TOP_EAST_NORTH);
			}
		}
		this.setSubBlock(SubBlockPosition.BOTTOM_EAST_SOUTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_EAST_NORTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_WEST_SOUTH);
		this.setSubBlock(SubBlockPosition.BOTTOM_WEST_NORTH);
		// if( map.hasOrHadMaterial( x, y, z+1, this.getMaterialUsedFor() ) ){
		// map.addSubBlock( p, SubBlockPosition.TOP_EAST_SOUTH,
		// materialReplacement);
		// }
		// else{
		// if( map.hasOrHadMaterial( x-1, y, z, new
		// StairsSouth().getMaterialUsedFor() ) ){ //negative corner
		// map.addSubBlock(p, SubBlockPosition.TOP_EAST_SOUTH,
		// materialReplacement);
		// map.addSubBlock(p, SubBlockPosition.TOP_WEST_SOUTH,
		// materialReplacement);
		// }
		// else if( !map.hasOrHadMaterial( x+1, y, z, new
		// StairsNorth().getMaterialUsedFor() ) ){ //no corner
		// map.addSubBlock(p, SubBlockPosition.TOP_EAST_SOUTH,
		// materialReplacement);
		// }
		// }
		// if( map.hasOrHadMaterial( x, y, z-1, this.getMaterialUsedFor() ) ){
		// map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH,
		// materialReplacement);
		// }
		// else{
		// if( map.hasOrHadMaterial( x-1, y, z, new
		// StairsNorth().getMaterialUsedFor() ) ){ //negative corner
		// map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH,
		// materialReplacement);
		// map.addSubBlock(p, SubBlockPosition.TOP_WEST_NORTH,
		// materialReplacement);
		// }
		// else if( !map.hasOrHadMaterial( x+1, y, z, new
		// StairsSouth().getMaterialUsedFor() ) ){ //no corner
		// map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH,
		// materialReplacement);
		// }
		// }
		// map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH,
		// materialReplacement);
		// map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH,
		// materialReplacement);
		// map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH,
		// materialReplacement);
		// map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH,
		// materialReplacement);
	}
}