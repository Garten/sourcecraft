package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
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
	private static Light LIGHT = new Light(Fire.FIRE_COLOR, Fire.distance50, Fire.distance100);
	private static EnvFire ENV_FIRE;

	public Fire() {
		int[] temp = { Material.FIRE };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position position, int material) {
		this.ENV_FIRE.setFireSize(this.map.getScale());
		this.map.setPointToGrid(position);
		this.map.movePointInGridDimension(0.5, 0, 0.5);
		this.map.movePointExactly(new Position(0, 1, 0));
		this.map.movePointInGridDimension(this.randomOffset(0.5), 0, this.randomOffset(0.5));
		this.map.addPointEntity(Fire.ENV_FIRE);
		this.map.movePointInGridDimension(0, 0.5, 0);
		this.map.movePointExactly(new Position(0, -1, 0));
		this.map.addPointEntity(Fire.LIGHT);
		this.map.markAsConverted(position);
	}

	private double randomOffset(double scale) {
		return scale;
	}
}
