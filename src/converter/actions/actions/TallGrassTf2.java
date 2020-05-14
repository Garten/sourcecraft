package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 *
 *
 */
public class TallGrassTf2 extends Action {

	private final static String MODEL = "models/props_swamp/tallgrass_01.mdl";
	private final static PropStatic TALL_GRASS = new PropStatic(TallGrassTf2.MODEL);

	public TallGrassTf2() {
		int[] temp = { MaterialLegacy.GRASS };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		context.setPointToGrid(p);
		context.movePointInGridDimension(0.5, 0, 0.5);
		int verticalRotation = (int) (Math.random() * 360);
		TallGrassTf2.TALL_GRASS.getAngles()
				.setY(verticalRotation);
		context.addPointEntity(TallGrassTf2.TALL_GRASS);
		context.markAsConverted(p);
	}

}
