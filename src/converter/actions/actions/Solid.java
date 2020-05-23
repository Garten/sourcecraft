package converter.actions.actions;

import basic.Loggger;
import converter.Skins;
import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.Orientation;

public class Solid extends Action {

	public static final Action INSTANCE = new Solid();

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
	public void add(Mapper context, Position position, Block block) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(position, block);
		if (Skins.INSTANCE.getSkin(block).materialFront.equals(Skins.DEFAULT_TEXTURE)) { // temp
			Loggger.log("no texture set for " + block.getName());
		}
		context.addSolid(context.createCuboid(position, end, block));
		context.markAsConverted(position, end);
	}
}
