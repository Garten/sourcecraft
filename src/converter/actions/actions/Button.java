package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import minecraft.Property;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class Button extends Action {
	@Override
	public void add(Mapper context, Position p, Block block) {
		this.handleFacingAndDir(context, p, block);
		context.markAsConverted(p);
	}

	private void handleFacingAndDir(Mapper context, Position p, Block block) {
		String face = block.getProperty(Property.face);
		int parts = 16;
		Position[] offsets = new Position[2];
		if (face.equals("wall")) {
			offsets = this.addWallButton(context, p, block);
		} else if (face.equals("ceiling")) {
			offsets = this.addCeilingButton(context, p, block);
		} else if (face.equals("floor")) {
			offsets = this.addFloorButton(context, p, block);
		}
		context.addSolidEntity(new FuncIllusionary(context.createCuboid(p, p, parts, offsets[0], offsets[1], block)));
	}

	private Position[] addWallButton(Mapper context, Position p, Block block) {
		Position[] out = new Position[2];
		String dir = block.getProperty(Property.facing);
		if (dir.equals("north")) {
			out[0] = new Position(5, 6, 14);
			out[1] = new Position(5, 6, 0);
		} else if (dir.equals("south")) {
			out[0] = new Position(5, 6, 0);
			out[1] = new Position(5, 6, 14);
		} else if (dir.equals("east")) {
			out[0] = new Position(0, 6, 5);
			out[1] = new Position(14, 6, 5);
		} else {
			out[0] = new Position(14, 6, 5);
			out[1] = new Position(0, 6, 5);
		}
		return out;
	}

	private Position[] addCeilingButton(Mapper context, Position p, Block block) {
		Position[] out = new Position[2];
		String dir = block.getProperty(Property.facing);
		if (dir.equals("north") || dir.equals("south")) {
			out[0] = new Position(5, 14, 6);
			out[1] = new Position(5, 0, 6);
		} else if (dir.equals("east") || dir.equals("west")) {
			out[0] = new Position(6, 14, 5);
			out[1] = new Position(6, 0, 5);
		}
		return out;
	}

	private Position[] addFloorButton(Mapper context, Position p, Block block) {
		Position[] out = new Position[2];
		String dir = block.getProperty(Property.facing);
		if (dir.equals("north") || dir.equals("south")) {
			out[0] = new Position(5, 0, 6);
			out[1] = new Position(5, 14, 6);
		} else if (dir.equals("east") || dir.equals("west")) {
			out[0] = new Position(6, 0, 5);
			out[1] = new Position(6, 14, 5);
		}
		return out;
	}
}
