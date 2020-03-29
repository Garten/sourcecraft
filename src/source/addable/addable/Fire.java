package source.addable.addable;

import cuboidFinder.CuboidFinder;
import minecraft.Position;
import minecraft.map.MinecraftMap;
import source.Material;
import source.addable.Addable;
import source.addable.AddableManager;
import vmfWriter.Color;
import vmfWriter.entity.pointEntity.pointEntity.EnvFire;
import vmfWriter.entity.pointEntity.pointEntity.Light;

public class Fire extends Addable {

	private final static int red = 255;
	private final static int green = 113;
	private final static int blue = 28;
	private final static int brigthness = 100;
	private final static int distance50 = 96;
	private final static int distance100 = 256;
	private final static Color FIRE_COLOR = new Color(Fire.red, Fire.green, Fire.blue, Fire.brigthness);
	private static Light LIGHT = new Light(Fire.FIRE_COLOR, Fire.distance50, Fire.distance100); // used as constant
	private static EnvFire ENV_FIRE; // used as constant

	public Fire() {
		int[] temp = { Material.FIRE };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void setAccess(CuboidFinder cf, MinecraftMap map, AddableManager manager) {
		super.setAccess(cf, map, manager);
		Fire.ENV_FIRE = new EnvFire(map.getScale() / 2);
	}

	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public void add(Position p, int material) {
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension(0.5, 0, 0.5);
		this.map.movePointExactly(new Position(0, 1, 0));
		this.map.movePointInGridDimension(this.randomOffset(0.5), 0, this.randomOffset(0.5));
		this.map.addPointEntity(Fire.ENV_FIRE);
		this.map.movePointInGridDimension(0, 0.5, 0);
		this.map.movePointExactly(new Position(0, -1, 0));
		this.map.addPointEntity(Fire.LIGHT);
		this.map.markAsConverted(p);
	}

	private double randomOffset(double scale) {
		return scale;
	}
}
