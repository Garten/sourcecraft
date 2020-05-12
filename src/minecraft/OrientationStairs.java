package minecraft;

import source.MaterialLegacy;
import vmfWriter.Orientation;

public enum OrientationStairs {
	SMALL_EAST_SOUTH(null, MaterialLegacy._STAIRS_SMALL_EAST_SOUTH),
	SMALL_EAST_NORTH(null, MaterialLegacy._STAIRS_SMALL_EAST_NORTH),
	SMALL_WEST_SOUTH(null, MaterialLegacy._STAIRS_SMALL_WEST_SOUTH),
	SMALL_WEST_NORTH(null, MaterialLegacy._STAIRS_SMALL_WEST_NORTH),
	BIG_EAST_SOUTH(null, MaterialLegacy._STAIRS_BIG_EAST_SOUTH),
	BIG_EAST_NORTH(null, MaterialLegacy._STAIRS_BIG_EAST_NORTH),
	BIG_WEST_SOUTH(null, MaterialLegacy._STAIRS_BIG_WEST_SOUTH),
	BIG_WEST_NORTH(null, MaterialLegacy._STAIRS_BIG_WEST_NORTH),
	EAST(Orientation.EAST, MaterialLegacy._STAIRS_EAST),
	WEST(Orientation.WEST, MaterialLegacy._STAIRS_WEST),
	NORTH(Orientation.NORTH, MaterialLegacy._STAIRS_NORTH),
	SOUTH(Orientation.SOUTH, MaterialLegacy._STAIRS_SOUTH);

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
