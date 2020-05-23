package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;

public class Debug extends Action {

	@Override
	public void add(Mapper context, Position position, Block block) {
		this.addDebugMarker(context, position, block);
	}

}
