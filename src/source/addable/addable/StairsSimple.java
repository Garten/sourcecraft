package source.addable.addable;

import java.util.LinkedList;
import java.util.List;

import minecraft.Area;
import minecraft.McaBlock;
import minecraft.McaBlockProperty;
import minecraft.Position;
import minecraft.SubBlockPosition;
import source.Material;
import source.addable.Addable;

public class StairsSimple extends Addable {

	private static final Position STEP_WEST = new Position(1, 0, 0);
	private static final Position STEP_EAST = new Position(-1, 0, 0);
	private static final Position STEP_NORTH = new Position(0, 0, 1);
	private static final Position STEP_SOUTH = new Position(0, 0, -1);
	private static final Position STEP_NORTH_WEST = Position.add(STEP_NORTH, STEP_WEST);
	private static final Position STEP_NORTH_EAST = Position.add(STEP_NORTH, STEP_EAST);
	private static final Position STEP_SOUTH_WEST = Position.add(STEP_SOUTH, STEP_WEST);
	private static final Position STEP_SOUTH_EAST = Position.add(STEP_SOUTH, STEP_EAST);

	public StairsSimple() {
		List<Integer> usedFor = new LinkedList<>();
		for (int id = 0; id < Material.__LENGTH_USEFUL; id++) {
			String name = Material.getName(id);
			if (name.contains("_stairs")) {
				usedFor.add(new Integer(id));
			}
		}
		// ugly and preliminary
		int[] usedForArray = new int[usedFor.size()];
		int i = 0;
		for (Integer id : usedFor) {
			usedForArray[i++] = id;
		}
		super.setMaterialUsedFor(usedForArray);
	}

	@Override
	public void add(Position position, int material) {
		McaBlock block = this.getProperties(material);
		Position end = this.cuboidFinder.getBestXY(position, material);
		for (Position pos : new Area(position, end)) {
			this.addDebugMarker(pos, material);
			this.addFinally(pos, block);
		}
	}

	public void addFinally(Position pos, McaBlock block) {
		this.addSubBlocks(pos, block);
		this.map.markAsConverted(pos);
	}

	private void addSubBlocks(Position position, McaBlock block) {
		int newMaterial = Material.getId(block.getName());
		this.handleHalf(position, newMaterial, block.getProperty(McaBlockProperty.half));
		this.handleFacing(position, newMaterial, block.getProperty(McaBlockProperty.half),
				block.getProperty(McaBlockProperty.facing), block.getProperty(McaBlockProperty.shape));
	}

	private McaBlock getProperties(int material) {
		McaBlock result = new McaBlock();
		result.setName(Material.getName(material));
		String[] parts = result.getName()
				.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (!parts[i].startsWith("stairs")) {
				sb.append(parts[i])
						.append("_");
				continue;
			}
			String details = parts[i];
			if (i + 1 < parts.length) {
				details = details + "_" + parts[i + 1];
			}
			String[] subparts = details.split("\\$");
			if (subparts.length < 4) {
				break;
			}
			result.addProperty(McaBlockProperty.half, subparts[1]);
			result.addProperty(McaBlockProperty.facing, subparts[2]);
			result.addProperty(McaBlockProperty.shape, subparts[3]);
			result.setName(sb.append("stairs")
					.toString());
			break;
		}
		return result;
	}

	private void handleFacing(Position position, int material, String half, String facing, String shape) {
		switch (McaBlockProperty.Half.valueOf(half)) {
		case top:
			switch (McaBlockProperty.Facing.valueOf(facing)) {
			case east:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				}
				break;
			case north:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				}
				break;
			case south:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				}
				break;
			case west:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_SOUTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_EAST_NORTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.BOTTOM_WEST_NORTH, material);
					break;
				}
				break;
			default:
				break;
			}
			break;
		case bottom:
		default:
			switch (McaBlockProperty.Facing.valueOf(facing)) {
			case east:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_EAST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_NORTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_NORTH_EAST);
					this.addRampStubEast(position);
					this.addRampStubNorth(position);
					this.map.addSubBlock(Position.add(position, STEP_NORTH_EAST), SubBlockPosition.BOTTOM_EAST_NORTH,
							Material._RAMP_NORTH_EAST);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_EAST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_SOUTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_SOUTH_EAST);
					this.addRampStubEast(position);
					this.addRampStubSouth(position);
					this.map.addSubBlock(Position.add(position, STEP_SOUTH_EAST), SubBlockPosition.BOTTOM_EAST_SOUTH,
							Material._RAMP_SOUTH_EAST);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_EAST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_EAST);
					this.addRampStubEast(position);
					break;
				}
				break;
			case north:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_NORTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_WEST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_NORTH_WEST);
					this.addRampStubNorth(position);
					this.addRampStubWest(position);
					this.map.addSubBlock(Position.add(position, STEP_NORTH_WEST), SubBlockPosition.BOTTOM_WEST_NORTH,
							Material._RAMP_NORTH_WEST);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_NORTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_EAST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_NORTH_EAST);
					this.addRampStubNorth(position);
					this.addRampStubEast(position);
					this.map.addSubBlock(Position.add(position, STEP_NORTH_EAST), SubBlockPosition.BOTTOM_EAST_NORTH,
							Material._RAMP_NORTH_EAST);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_NORTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_NORTH);
					this.addRampStubNorth(position);
					break;
				}
				break;
			case south:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_SOUTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_EAST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_SOUTH_EAST);
					this.addRampStubSouth(position);
					this.addRampStubEast(position);
					this.map.addSubBlock(Position.add(position, STEP_SOUTH_EAST), SubBlockPosition.BOTTOM_EAST_SOUTH,
							Material._RAMP_SOUTH_EAST);
					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_SOUTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_WEST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_SOUTH_WEST);
					this.addRampStubSouth(position);
					this.addRampStubWest(position);
					this.map.addSubBlock(Position.add(position, STEP_SOUTH_WEST), SubBlockPosition.BOTTOM_WEST_SOUTH,
							Material._RAMP_SOUTH_WEST);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_SOUTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_SOUTH);
					this.addRampStubSouth(position);
					break;
				}
				break;
			case west:
				switch (McaBlockProperty.Shape.valueOf(shape)) {
				case inner_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, material);
					break;
				case inner_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, material);
					break;
				case outer_left:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_WEST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, Material._RAMP_SOUTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_SOUTH_WEST);
					this.addRampStubWest(position);
					this.addRampStubSouth(position);
					this.map.addSubBlock(Position.add(position, STEP_SOUTH_WEST), SubBlockPosition.BOTTOM_WEST_SOUTH,
							Material._RAMP_SOUTH_WEST);

					break;
				case outer_right:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_WEST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, Material._RAMP_NORTH);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_NORTH_WEST);
					this.addRampStubWest(position);
					this.addRampStubNorth(position);
					this.map.addSubBlock(Position.add(position, STEP_NORTH_WEST), SubBlockPosition.BOTTOM_WEST_NORTH,
							Material._RAMP_NORTH_WEST);
					break;
				case straight:
				default:
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_SOUTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_WEST_NORTH, material);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_SOUTH, Material._RAMP_WEST);
					this.map.addSubBlock(position, SubBlockPosition.TOP_EAST_NORTH, Material._RAMP_WEST);
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
		this.map.addSubBlock(Position.add(position, STEP_WEST), SubBlockPosition.BOTTOM_WEST_SOUTH,
				Material._RAMP_WEST);
		this.map.addSubBlock(Position.add(position, STEP_WEST), SubBlockPosition.BOTTOM_WEST_NORTH,
				Material._RAMP_WEST);
	}

	private void addRampStubEast(Position position) {
		this.map.addSubBlock(Position.add(position, STEP_EAST), SubBlockPosition.BOTTOM_EAST_SOUTH,
				Material._RAMP_EAST);
		this.map.addSubBlock(Position.add(position, STEP_EAST), SubBlockPosition.BOTTOM_EAST_NORTH,
				Material._RAMP_EAST);
	}

	private void addRampStubNorth(Position position) {
		this.map.addSubBlock(Position.add(position, STEP_NORTH), SubBlockPosition.BOTTOM_EAST_NORTH,
				Material._RAMP_NORTH);
		this.map.addSubBlock(Position.add(position, STEP_NORTH), SubBlockPosition.BOTTOM_WEST_NORTH,
				Material._RAMP_NORTH);
	}

	private void addRampStubSouth(Position position) {
		this.map.addSubBlock(Position.add(position, STEP_SOUTH), SubBlockPosition.BOTTOM_EAST_SOUTH,
				Material._RAMP_SOUTH);
		this.map.addSubBlock(Position.add(position, STEP_SOUTH), SubBlockPosition.BOTTOM_WEST_SOUTH,
				Material._RAMP_SOUTH);
	}

	private void handleHalf(Position position, int material, String part) {
		SubBlockPosition[] subpos;
		if (part.equals("bottom")) {
			subpos = new SubBlockPosition[] { SubBlockPosition.BOTTOM_EAST_SOUTH, SubBlockPosition.BOTTOM_EAST_NORTH,
					SubBlockPosition.BOTTOM_WEST_SOUTH, SubBlockPosition.BOTTOM_WEST_NORTH };
		} else {
			subpos = new SubBlockPosition[] { SubBlockPosition.TOP_EAST_SOUTH, SubBlockPosition.TOP_EAST_NORTH,
					SubBlockPosition.TOP_WEST_SOUTH, SubBlockPosition.TOP_WEST_NORTH };
		}
		for (SubBlockPosition sub : subpos) {
			this.map.addSubBlock(position, sub, material);
		}
	}
}
