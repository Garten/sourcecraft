package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Area;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Position;
import minecraft.Property;
import minecraft.SubBlockPosition;

public class Stairs extends Action {

	private static final Position STEP_WEST = new Position(1, 0, 0);
	private static final Position STEP_EAST = new Position(-1, 0, 0);
	private static final Position STEP_NORTH = new Position(0, 0, 1);
	private static final Position STEP_SOUTH = new Position(0, 0, -1);
	private static final Position STEP_NORTH_WEST = Position.add(STEP_NORTH, STEP_WEST);
	private static final Position STEP_NORTH_EAST = Position.add(STEP_NORTH, STEP_EAST);
	private static final Position STEP_SOUTH_WEST = Position.add(STEP_SOUTH, STEP_WEST);
	private static final Position STEP_SOUTH_EAST = Position.add(STEP_SOUTH, STEP_EAST);

	private Mapper context;

	@Override
	public void add(Mapper context, Position position, Block block) {
		this.context = context;
		Position end = this.context.getCuboidFinder()
				.getBestXY(position, block);
		for (Position pos : new Area(position, end)) {
			// this.addDebugMarker(context, pos, block);
			this.addFinally(context, pos, block);
		}
	}

	public void addFinally(Mapper context, Position pos, Block block) {
		this.addSubBlocks(pos, block);
		context.markAsConverted(pos);
	}

	private void addSubBlocks(Position position, Block block) {
		this.handleHalf(position, block, block.getProperty(Property.half));
		this.handleFacing(position, block, block.getProperty(Property.half), block.getProperty(Property.facing),
				block.getProperty(Property.shape));
	}

	private void handleFacing(Position position, Block material, String half, String facing, String shape) {
		switch (Property.Half.valueOf(half)) {
		case top:
			switch (Property.Facing.valueOf(facing)) {
			case east:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				}
				break;
			case north:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				}
				break;
			case south:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				}
				break;
			case west:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				}
				break;
			default:
				break;
			}
			break;
		case bottom:
		default:
			switch (Property.Facing.valueOf(facing)) {
			case east:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_EAST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_NORTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_NORTH_EAST);
					this.addRampStubEast(position);
					this.addRampStubNorth(position);
					this.context.addSubBlock(Position.add(position, STEP_NORTH_EAST),
							SubBlockPosition.BOTTOM_EAST_NORTH, Blocks._RAMP_NORTH_EAST);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_EAST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_SOUTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_SOUTH_EAST);
					this.addRampStubEast(position);
					this.addRampStubSouth(position);
					this.context.addSubBlock(Position.add(position, STEP_SOUTH_EAST),
							SubBlockPosition.BOTTOM_EAST_SOUTH, Blocks._RAMP_SOUTH_EAST);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_EAST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_EAST);
					this.addRampStubEast(position);
					break;
				}
				break;
			case north:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_NORTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_WEST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_NORTH_WEST);
					this.addRampStubNorth(position);
					this.addRampStubWest(position);
					this.context.addSubBlock(Position.add(position, STEP_NORTH_WEST),
							SubBlockPosition.BOTTOM_WEST_NORTH, Blocks._RAMP_NORTH_WEST);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_NORTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_EAST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_NORTH_EAST);
					this.addRampStubNorth(position);
					this.addRampStubEast(position);
					this.context.addSubBlock(Position.add(position, STEP_NORTH_EAST),
							SubBlockPosition.BOTTOM_EAST_NORTH, Blocks._RAMP_NORTH_EAST);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_NORTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_NORTH);
					this.addRampStubNorth(position);
					break;
				}
				break;
			case south:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_SOUTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_EAST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_SOUTH_EAST);
					this.addRampStubSouth(position);
					this.addRampStubEast(position);
					this.context.addSubBlock(Position.add(position, STEP_SOUTH_EAST),
							SubBlockPosition.BOTTOM_EAST_SOUTH, Blocks._RAMP_SOUTH_EAST);
					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_SOUTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_WEST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_SOUTH_WEST);
					this.addRampStubSouth(position);
					this.addRampStubWest(position);
					this.context.addSubBlock(Position.add(position, STEP_SOUTH_WEST),
							SubBlockPosition.BOTTOM_WEST_SOUTH, Blocks._RAMP_SOUTH_WEST);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_SOUTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_SOUTH);
					this.addRampStubSouth(position);
					break;
				}
				break;
			case west:
				switch (Property.Shape.valueOf(shape)) {
				case inner_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					break;
				case inner_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					break;
				case outer_left:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_WEST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Blocks._RAMP_SOUTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_SOUTH_WEST);
					this.addRampStubWest(position);
					this.addRampStubSouth(position);
					this.context.addSubBlock(Position.add(position, STEP_SOUTH_WEST),
							SubBlockPosition.BOTTOM_WEST_SOUTH, Blocks._RAMP_SOUTH_WEST);

					break;
				case outer_right:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_WEST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Blocks._RAMP_NORTH);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_NORTH_WEST);
					this.addRampStubWest(position);
					this.addRampStubNorth(position);
					this.context.addSubBlock(Position.add(position, STEP_NORTH_WEST),
							SubBlockPosition.BOTTOM_WEST_NORTH, Blocks._RAMP_NORTH_WEST);
					break;
				case straight:
				default:
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Blocks._RAMP_WEST);
					this.context.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Blocks._RAMP_WEST);
					this.addRampStubWest(position);
					break;
				}
				break;
			default:
				break;
			}
		}
	}

	private void addRampStubWest(Position position) {
		Position p = Position.add(position, STEP_WEST);
		if (context.getSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH) != Blocks._UNSET
				&& context.getSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH) != Blocks._UNSET)
			return;
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, Blocks._RAMP_WEST);
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH, Blocks._RAMP_WEST);
	}

	private void addRampStubEast(Position position) {
		Position p = Position.add(position, STEP_EAST);
		if (context.getSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH) != Blocks._UNSET
				&& context.getSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH) != Blocks._UNSET)
			return;
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH, Blocks._RAMP_EAST);
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH, Blocks._RAMP_EAST);
	}

	private void addRampStubNorth(Position position) {
		Position p = Position.add(position, STEP_NORTH);
		if (context.getSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH) != Blocks._UNSET
				&& context.getSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH) != Blocks._UNSET)
			return;
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH, Blocks._RAMP_NORTH);
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH, Blocks._RAMP_NORTH);
	}

	private void addRampStubSouth(Position position) {
		Position p = Position.add(position, STEP_SOUTH);
		if (context.getSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH) != Blocks._UNSET
				&& context.getSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH) != Blocks._UNSET)
			return;
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH, Blocks._RAMP_SOUTH);
		this.context.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, Blocks._RAMP_SOUTH);
	}

	private void handleHalf(Position position, Block material, String part) {
		SubBlockPosition[] subpos;
		if (part.equals("bottom")) {
			subpos = new SubBlockPosition[] { SubBlockPosition.BOTTOM_EAST_SOUTH, SubBlockPosition.BOTTOM_EAST_NORTH,
					SubBlockPosition.BOTTOM_WEST_SOUTH, SubBlockPosition.BOTTOM_WEST_NORTH };
		} else {
			subpos = new SubBlockPosition[] { SubBlockPosition.TOP_EAST_SOUTH, SubBlockPosition.TOP_EAST_NORTH,
					SubBlockPosition.TOP_WEST_SOUTH, SubBlockPosition.TOP_WEST_NORTH };
		}
		for (SubBlockPosition sub : subpos) {
			this.context.addSubBlock(position, sub, material);
		}
	}
}
