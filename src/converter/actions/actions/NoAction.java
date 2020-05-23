package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;

public class NoAction extends Action {

	public static final NoAction INSTANCE = new NoAction();

	@Override
	public void add(Mapper context, Position position, Block material) {
		context.markAsConverted(position);
	}

}
