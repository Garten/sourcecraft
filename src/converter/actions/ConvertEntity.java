package converter.actions;

import java.util.function.Supplier;

import converter.actions.actions.NoAction;
import minecraft.Block;

public class ConvertEntity {
	private Supplier<Block> block;
	private Action action = NoAction.INSTANCE;

	public ConvertEntity(Supplier<Block> block, Action action) {
		this.block = block;
		this.action = action;
	}

	public Supplier<Block> getBlock() {
		return this.block;
	}

	public ConvertEntity setBlock(Supplier<Block> block) {
		this.block = block;
		return this;
	}

	public Action getAction() {
		return this.action;
	}

	public ConvertEntity setAction(Action action) {
		this.action = action;
		return this;
	}
}
