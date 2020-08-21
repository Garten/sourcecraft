package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import minecraft.Property;
import minecraft.SubBlockPosition;

public class Slab extends Action {

	private Mapper context;

	@Override
	public void add(Mapper context, Position position, Block block) {
		this.context = context;
		String half = block.getProperty(Property.type);
		if (half.equals("double")) {
			this.handleFull(position, block, half);
		} else {
			this.handleHalf(position, block, half);
		}
		context.markAsConverted(position);
	}

	private void handleFull(Position position, Block block, String half) {
		SubBlockPosition[] subpos = new SubBlockPosition[] { SubBlockPosition.BOTTOM_EAST_SOUTH,
				SubBlockPosition.BOTTOM_EAST_NORTH, SubBlockPosition.BOTTOM_WEST_SOUTH,
				SubBlockPosition.BOTTOM_WEST_NORTH, SubBlockPosition.TOP_EAST_SOUTH, SubBlockPosition.TOP_EAST_NORTH,
				SubBlockPosition.TOP_WEST_SOUTH, SubBlockPosition.TOP_WEST_NORTH };
		for (SubBlockPosition sub : subpos) {
			this.context.addSubBlock(position, sub, block);
		}
	}

	private void handleHalf(Position position, Block block, String part) {
		SubBlockPosition[] subpos;
		if (part.equals("bottom")) {
			subpos = new SubBlockPosition[] { SubBlockPosition.BOTTOM_EAST_SOUTH, SubBlockPosition.BOTTOM_EAST_NORTH,
					SubBlockPosition.BOTTOM_WEST_SOUTH, SubBlockPosition.BOTTOM_WEST_NORTH };
		} else {
			subpos = new SubBlockPosition[] { SubBlockPosition.TOP_EAST_SOUTH, SubBlockPosition.TOP_EAST_NORTH,
					SubBlockPosition.TOP_WEST_SOUTH, SubBlockPosition.TOP_WEST_NORTH };
		}
		for (SubBlockPosition sub : subpos) {
			this.context.addSubBlock(position, sub, block);
		}
	}

}
