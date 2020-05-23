package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.entity.solidEntity.FuncDetail;

/**
 * creates a "func_detail"-Block
 */
public class DetailBlock extends Action {

	@Override
	public void add(Mapper context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXYZ(p, material);
		context.addSolidEntity(new FuncDetail(context.createCuboid(p, end, material)));
		context.markAsConverted(p, end);
	}
}
