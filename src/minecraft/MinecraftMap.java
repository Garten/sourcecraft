package minecraft;

import java.util.Collection;
import java.util.function.Predicate;

public abstract class MinecraftMap {

	public abstract Block getBlock(Position position);

	public abstract void setBlock(Position position, Block block);

	public boolean hasBlock(Position position, Predicate<Block> container) {
		return container.test(this.getBlock(position));
	}

	public boolean hasBlock(Position position, Collection<Block> blocks) {
		return this.hasBlock(position, block -> blocks.contains(block));
	}
}
