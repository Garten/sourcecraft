package source.addable.addable.tf2;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 *
 *
 */
public class TallGrassTf2 extends ConvertAction {

	private final static String MODEL = "models/props_swamp/tallgrass_01.mdl";
	private final static PropStatic TALL_GRASS = new PropStatic(TallGrassTf2.MODEL);

	public TallGrassTf2() {
		int[] temp = { MaterialLegacy.GRASS };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		context.setPointToGrid(p);
		context.movePointInGridDimension(0.5, 0, 0.5);
		int verticalRotation = (int) (Math.random() * 360);
		TallGrassTf2.TALL_GRASS.getAngles()
				.setY(verticalRotation);
		context.addPointEntity(TallGrassTf2.TALL_GRASS);
		context.markAsConverted(p);
	}

}
