package source.addable;

import java.util.function.Supplier;

import minecraft.Block;
import source.addable.addable.NoAction;

public class ConvertEntity {
	private Supplier<Block> block;
	private ConvertAction action = NoAction.INSTANCE;

	public ConvertEntity(Supplier<Block> block, ConvertAction action) {
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

	public ConvertAction getAction() {
		return this.action;
	}

	public ConvertEntity setAction(ConvertAction action) {
		this.action = action;
		return this;
	}
}
