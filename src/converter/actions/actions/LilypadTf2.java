package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 *
 *
 */
public class LilypadTf2 extends Action {

	private final static String MODEL = "models/props_swamp/lilypad_large.mdl";
	private final static PropStatic LILY_PAD = new PropStatic(LilypadTf2.MODEL);

	@Override
	public void add(Mapper context, Position position, Block material) {
		context.setPointToGrid(position);
		context.movePointInGridDimension(0.5, 0, 0.5);
		int verticalAngle = (int) (Math.random() * 360);
		LilypadTf2.LILY_PAD.getAngles()
				.setY(verticalAngle);
		context.addPointEntity(LilypadTf2.LILY_PAD);
		context.markAsConverted(position);
	}

}
