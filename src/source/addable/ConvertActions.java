package source.addable;

import basic.Tuple;
import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.addable.addable.NoAction;

public class ConvertActions {

	protected BlockMap<ConvertAction> actions;

	public ConvertActions(ConvertAction fallBack) {
		this.actions = new BlockMap<ConvertAction>().setDefault(fallBack);
	}

	public ConvertActions setAction(Block block, ConvertAction addable) {
		this.actions.put(block, addable);
		return this;
	}

	public ConvertActions setActions(Iterable<Tuple<Block, ConvertAction>> actions) {
		actions.forEach(a -> this.setAction(a.getFirst(), a.getSecond()));
		return this;
	}

	public ConvertAction getAction(Block block) {
		return this.actions.get(block);
	}

	public void add(ConverterContext context, Position position, Block block) {
		ConvertAction action;
		if (block == null) {
			action = NoAction.INSTANCE;
		} else {
			action = this.actions.getFallBackToSuffix(block);
		}
		action.add(context, position, block);
	}
}
