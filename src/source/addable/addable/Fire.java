package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;
import vmfWriter.Color;
import vmfWriter.entity.pointEntity.pointEntity.EnvFire;
import vmfWriter.entity.pointEntity.pointEntity.Light;

public class Fire extends ConvertAction {

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
		int[] temp = { MaterialLegacy.FIRE };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position position, Block material) {
		this.ENV_FIRE.setFireSize(context.getScale());
		context.setPointToGrid(position);
		context.movePointInGridDimension(0.5, 0, 0.5);
		context.movePointExactly(new Position(0, 1, 0));
		context.movePointInGridDimension(this.randomOffset(0.5), 0, this.randomOffset(0.5));
		context.addPointEntity(Fire.ENV_FIRE);
		context.movePointInGridDimension(0, 0.5, 0);
		context.movePointExactly(new Position(0, -1, 0));
		context.addPointEntity(Fire.LIGHT);
		context.markAsConverted(position);
	}

	private double randomOffset(double scale) {
		return scale;
	}
}
