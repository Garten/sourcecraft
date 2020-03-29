package minecraft;

import basic.Loggger;

public enum SubBlockPosition {
	// order is crucial
	BOTTOM_WEST_NORTH,
	BOTTOM_WEST_SOUTH,
	BOTTOM_EAST_NORTH,
	BOTTOM_EAST_SOUTH,
	TOP_WEST_NORTH,
	TOP_WEST_SOUTH,
	TOP_EAST_NORTH,
	TOP_EAST_SOUTH;

	public static int getPos(SubBlockPosition pos) {
		switch (pos) {
		case BOTTOM_WEST_NORTH:
			return 0;
		case BOTTOM_WEST_SOUTH:
			return 1;
		case BOTTOM_EAST_NORTH:
			return 2;
		case BOTTOM_EAST_SOUTH:
			return 3;
		case TOP_WEST_NORTH:
			return 4;
		case TOP_WEST_SOUTH:
			return 5;
		case TOP_EAST_NORTH:
			return 6;
		case TOP_EAST_SOUTH:
			return 7;
		default:
			Loggger.warn("default case of subBlocks occured");
			return 0;
		}
	}

	public static Position getPosition(SubBlockPosition pos) {
		int x = 1;
		int y = 1;
		int z = 1;
		Position position;
		switch (pos) {
		case BOTTOM_EAST_SOUTH:
			position = new Position(x, y - 1, z);
			break;
		case BOTTOM_EAST_NORTH:
			position = new Position(x, y - 1, z - 1);
			break;
		case BOTTOM_WEST_SOUTH:
			position = new Position(x - 1, y - 1, z);
			break;
		case BOTTOM_WEST_NORTH:
			position = new Position(x - 1, y - 1, z - 1);
			break;
		case TOP_EAST_SOUTH:
			position = new Position(x, y, z);
			break;
		case TOP_EAST_NORTH:
			position = new Position(x, y, z - 1);
			break;
		case TOP_WEST_SOUTH:
			position = new Position(x - 1, y, z);
			break;
		case TOP_WEST_NORTH:
			position = new Position(x - 1, y, z - 1);
			break;
		default:
			position = new Position(1, 1, 1);
			Loggger.warn("default cse of subBlocks occured");
			break;
		}
		return position;
	}
}
