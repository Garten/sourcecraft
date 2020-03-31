package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.Angles;
import vmfWriter.Color;
import vmfWriter.entity.pointEntity.pointEntity.Light;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 * Add torch props and light entities
 *
 */
public class CssLamp extends Addable {

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
	private final static PropStatic TORCH_HOLDER_WEST = new PropStatic(CssLamp.TORCH_HOLDER_MODEL, new Angles(0, 180, 0));
	private final static PropStatic TORCH_HOLDER_SOUTH = new PropStatic(CssLamp.TORCH_HOLDER_MODEL, new Angles(0, 270, 0));
	private final static PropStatic TORCH_HOLDER_NORTH = new PropStatic(CssLamp.TORCH_HOLDER_MODEL, new Angles(0, 90, 0));

	public CssLamp() {
		int[] temp = { Material.TORCH, Material.WALL_TORCH_EAST, Material.WALL_TORCH_WEST, Material.WALL_TORCH_SOUTH, Material.WALL_TORCH_NORTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		int d = 0;
		// x=EAST
		// z=SOUTH
		this.map.setPointToGrid(p);

		if (material == 50) { // on ground
			this.map.movePointInGridDimension(0.5, 0, 0.5);
			this.map.movePointExactly(new Position(0, 10, 0));
		} else if (material == 341) { // pointing east
			this.map.movePointInGridDimension(0, 0, 0.5);
			this.map.movePointExactly(new Position(0, -6, 0));
			this.map.addPointEntity(CssLamp.TORCH_HOLDER_EAST);
			this.map.movePointExactly(new Position(8, 22, 0));
			d = 1;
		} else if (material == 342) { // pointing west
			this.map.movePointInGridDimension(1, 0, 0.5);
			this.map.movePointExactly(new Position(0, -6, 0));
			this.map.addPointEntity(CssLamp.TORCH_HOLDER_WEST);
			this.map.movePointExactly(new Position(0 - 8, 22, 0));
			d = 2;
		} else if (material == 343) { // pointing south // !
			this.map.movePointInGridDimension(0.5, 0, 0);
			this.map.movePointExactly(new Position(0, -6, 0));
			this.map.addPointEntity(CssLamp.TORCH_HOLDER_SOUTH);
			this.map.movePointExactly(new Position(0, 22, 8));
			d = 4;
		} else if (material == 344) { // pointing north
			this.map.movePointInGridDimension(0.5, 0, 1);
			this.map.movePointExactly(new Position(0, -6, 0));
			this.map.addPointEntity(CssLamp.TORCH_HOLDER_NORTH);
			this.map.movePointExactly(new Position(0, 22, -8));
			d = 3;

		}
		this.map.addPointEntity(CssLamp.TORCH);

		this.map.movePointExactly(new Position(10, 0, 0));
		if (d != 2) {
			this.map.addPointEntity(CssLamp.LIGHT);
			// map.addLight( red, green, blue, brigthness, distance50,
			// distance100 );
		}
		this.map.movePointExactly(new Position(-20, 0, 0));
		if (d != 1) {
			this.map.addPointEntity(CssLamp.LIGHT);
			// map.addLight( red, green, blue, brigthness, distance50,
			// distance100 );
		}
		this.map.movePointExactly(new Position(10, 0, 0));
		this.map.movePointExactly(new Position(0, 0, 10));
		if (d != 3) {
			this.map.addPointEntity(CssLamp.LIGHT);
			// map.addLight( red, green, blue, brigthness, distance50,
			// distance100 );
		}
		this.map.movePointExactly(new Position(0, 0, -20));
		if (d != 4) {
			this.map.addPointEntity(CssLamp.LIGHT);
			// map.addLight( red, green, blue, brigthness, distance50,
			// distance100 );
		}
		this.map.markAsConverted(p);
	}

}
