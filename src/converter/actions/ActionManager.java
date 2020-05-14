package converter.actions;

import basic.Tuple;
import converter.actions.actions.NoAction;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;

public class ActionManager {

	protected BlockMap<Action> actions;

	public ActionManager(Action fallBack) {
		this.actions = new BlockMap<Action>().setDefault(fallBack);
	}

	public ActionManager setAction(Block block, Action addable) {
		this.actions.put(block, addable);
		return this;
	}

	public ActionManager setActions(Iterable<Tuple<Block, Action>> actions) {
		actions.forEach(a -> this.setAction(a.getFirst(), a.getSecond()));
		return this;
	}

	public Action getAction(Block block) {
		return this.actions.getFallBackToSuffix(block);
	}

	public void add(Mapper context, Position position, Block block) {
		Action action;
		if (block == null) {
			action = NoAction.INSTANCE;
		} else {
			action = this.getAction(block);
		}
		action.add(context, position, block);
	}

	public void print() {
		actions.print();
	}
}
