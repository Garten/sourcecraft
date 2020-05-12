package source.addable.addable;

import basic.Loggger;
import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.SkinManager;
import source.addable.ConvertAction;
import vmfWriter.Orientation;

public class Solid extends ConvertAction {

	public static final ConvertAction INSTANCE = new Solid();

	public Solid() {
		// is default addable
	}

	@Override
	public boolean isAirBlock() {
		return false;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return true;
	}

	@Override
	public void add(ConverterContext context, Position position, Block block) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(position, block);
		if (SkinManager.INSTANCE.getSkin(block).materialFront.equals(SkinManager.DEFAULT_TEXTURE)) { // temp
			Loggger.log("no texture set for " + block.getName());
		}
		context.addSolid(context.createCuboid(position, end, block));
		context.markAsConverted(position, end);
	}
}
