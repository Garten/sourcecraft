package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.MaterialLegacy;
import minecraft.Position;
import minecraft.Property;
import vmfWriter.Angles;
import vmfWriter.Color;
import vmfWriter.entity.pointEntity.pointEntity.Light;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 * Add torch props and light entities
 *
 */
public class CssLamp extends Action {

	private final static String TORCH_MODEL = "models/props/cs_italy/it_lantern1.mdl";
	private final static String TORCH_HOLDER_MODEL = "models/props/cs_italy/it_lampholder1.mdl";

	private final static int red = 255;
	private final static int green = 255;
	private final static int blue = 150;
	private final static int brigthness = 10;
	private final static int distance50 = 96;
	private final static int distance100 = 256;
	private final static Color LIGHT_COLOR = new Color(CssLamp.red, CssLamp.green, CssLamp.blue, CssLamp.brigthness);
	private final static Light LIGHT = new Light(CssLamp.LIGHT_COLOR, CssLamp.distance50, CssLamp.distance100);
	private final static PropStatic TORCH = new PropStatic(CssLamp.TORCH_MODEL);
	private final static PropStatic TORCH_HOLDER_EAST = new PropStatic(CssLamp.TORCH_HOLDER_MODEL, new Angles(0, 0, 0));
	private final static PropStatic TORCH_HOLDER_WEST = new PropStatic(CssLamp.TORCH_HOLDER_MODEL,
			new Angles(0, 180, 0));
	private final static PropStatic TORCH_HOLDER_SOUTH = new PropStatic(CssLamp.TORCH_HOLDER_MODEL,
			new Angles(0, 270, 0));
	private final static PropStatic TORCH_HOLDER_NORTH = new PropStatic(CssLamp.TORCH_HOLDER_MODEL,
			new Angles(0, 90, 0));

	public CssLamp() {
		int[] temp = { MaterialLegacy.TORCH, MaterialLegacy.WALL_TORCH$EAST, MaterialLegacy.WALL_TORCH$WEST,
				MaterialLegacy.WALL_TORCH$SOUTH, MaterialLegacy.WALL_TORCH$NORTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position p, Block block) {
		int d = 0;
		// x=EAST
		// z=SOUTH
		context.setPointToGrid(p);

		if (block.equals(Material.torch.get())) { // on ground
			context.movePointInGridDimension(0.5, 0, 0.5);
			context.movePointExactly(new Position(0, 10, 0));
		} else if (block.equals(Blocks.get(t -> t.setName(Material.wall_torch)
				.addProperty(Property.facing, Property.Facing.east)))) { // pointing east
																			// block == 341
			context.movePointInGridDimension(0, 0, 0.5);
			context.movePointExactly(new Position(0, -6, 0));
			context.addPointEntity(CssLamp.TORCH_HOLDER_EAST);
			context.movePointExactly(new Position(8, 22, 0));
			d = 1;
		} else if (block.equals(Blocks.get(t -> t.setName(Material.wall_torch)
				.addProperty(Property.facing, Property.Facing.west)))) { // pointing west
			context.movePointInGridDimension(1, 0, 0.5);
			context.movePointExactly(new Position(0, -6, 0));
			context.addPointEntity(CssLamp.TORCH_HOLDER_WEST);
			context.movePointExactly(new Position(0 - 8, 22, 0));
			d = 2;
		} else if (block.equals(Blocks.get(t -> t.setName(Material.wall_torch)
				.addProperty(Property.facing, Property.Facing.south)))) { // pointing south // !
			context.movePointInGridDimension(0.5, 0, 0);
			context.movePointExactly(new Position(0, -6, 0));
			context.addPointEntity(CssLamp.TORCH_HOLDER_SOUTH);
			context.movePointExactly(new Position(0, 22, 8));
			d = 4;
		} else if (block.equals(Blocks.get(t -> t.setName(Material.wall_torch)
				.addProperty(Property.facing, Property.Facing.north)))) { // pointing north
			context.movePointInGridDimension(0.5, 0, 1);
			context.movePointExactly(new Position(0, -6, 0));
			context.addPointEntity(CssLamp.TORCH_HOLDER_NORTH);
			context.movePointExactly(new Position(0, 22, -8));
			d = 3;

		}
		context.addPointEntity(CssLamp.TORCH);

		context.movePointExactly(new Position(10, 0, 0));
		if (d != 2) {
			context.addPointEntity(CssLamp.LIGHT);
		}
		context.movePointExactly(new Position(-20, 0, 0));
		if (d != 1) {
			context.addPointEntity(CssLamp.LIGHT);
		}
		context.movePointExactly(new Position(10, 0, 0));
		context.movePointExactly(new Position(0, 0, 10));
		if (d != 3) {
			context.addPointEntity(CssLamp.LIGHT);
		}
		context.movePointExactly(new Position(0, 0, -20));
		if (d != 4) {
			context.addPointEntity(CssLamp.LIGHT);
		}
		context.markAsConverted(p);
	}

}
