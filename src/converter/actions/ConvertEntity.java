package converter.actions;

import java.util.function.Supplier;

import converter.actions.actions.NoAction;
import minecraft.Block;
import minecraft.BlockTemplate;

public class ConvertEntity {
	private BlockTemplate block;
	private Action action = NoAction.INSTANCE;

	public ConvertEntity() {

	}

	public ConvertEntity(BlockTemplate block, Action action) {
		this.block = block;
		this.action = action;
	}

	public Supplier<Block> getBlock() {
		return this.block;
	}

	// not used by gson
	public ConvertEntity setBlock(BlockTemplate block) {
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
