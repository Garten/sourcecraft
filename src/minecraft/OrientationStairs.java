package minecraft;

import source.Material;
import vmfWriter.Orientation;

public enum OrientationStairs {
	SMALL_EAST_SOUTH(null, Material._STAIRS_SMALL_EAST_SOUTH),
	SMALL_EAST_NORTH(null, Material._STAIRS_SMALL_EAST_NORTH),
	SMALL_WEST_SOUTH(null, Material._STAIRS_SMALL_WEST_SOUTH),
	SMALL_WEST_NORTH(null, Material._STAIRS_SMALL_WEST_NORTH),
	BIG_EAST_SOUTH(null, Material._STAIRS_BIG_EAST_SOUTH),
	BIG_EAST_NORTH(null, Material._STAIRS_BIG_EAST_NORTH),
	BIG_WEST_SOUTH(null, Material._STAIRS_BIG_WEST_SOUTH),
	BIG_WEST_NORTH(null, Material._STAIRS_BIG_WEST_NORTH),
	EAST(Orientation.EAST, Material._STAIRS_EAST),
	WEST(Orientation.WEST, Material._STAIRS_WEST),
	NORTH(Orientation.NORTH, Material._STAIRS_NORTH),
	SOUTH(Orientation.SOUTH, Material._STAIRS_SOUTH);

	private final Orientation orientation;
	private final int material;

	private OrientationStairs(Orientation orientation, int material) {
		this.orientation = orientation;
		this.material = material;
	}

	public Orientation getOrientation() {
		return this.orientation;
	}

	public int getMaterial() {
		return this.material;
	}
}
