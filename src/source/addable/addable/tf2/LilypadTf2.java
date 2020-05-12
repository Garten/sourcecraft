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
public class LilypadTf2 extends ConvertAction {

	private final static String MODEL = "models/props_swamp/lilypad_large.mdl";
	private final static PropStatic LILY_PAD = new PropStatic(LilypadTf2.MODEL);

	public LilypadTf2() {
		int[] temp = { MaterialLegacy.LILY_PAD };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(ConverterContext context, Position position, Block material) {
		context.setPointToGrid(position);
		context.movePointInGridDimension(0.5, 0, 0.5);
		int verticalAngle = (int) (Math.random() * 360);
		LilypadTf2.LILY_PAD.getAngles()
				.setY(verticalAngle);
		context.addPointEntity(LilypadTf2.LILY_PAD);
		context.markAsConverted(position);
	}

}
