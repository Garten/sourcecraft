package cuboidFinder;

import java.util.Arrays;
import java.util.Collection;

import minecraft.Block;
import minecraft.Position;

public abstract class CuboidFinder {

	public Position getBestXYZ(Position position, Block... material) {
		return this.getBestXYZ(position, Arrays.asList(material));
	}

	public Position getBestXY(Position position, Block... material) {
		return this.getBestXY(position, Arrays.asList(material));
	}

	public Position getBestXZ(Position position, Block... material) {
		return this.getBestXZ(position, Arrays.asList(material));
	}

	public Position getBestYZ(Position position, Block... material) {
		return this.getBestYZ(position, Arrays.asList(material));
	}

	public Position getBestX(Position position, Block... material) {
		return this.getBestX(position, Arrays.asList(material));
	}

	public Position getBestY(Position position, Block... material) {
		return this.getBestY(position, Arrays.asList(material));
	}

	public Position getBestZ(Position position, Block... material) {
		return this.getBestZ(position, Arrays.asList(material));
	}

	public abstract Position getBestXYZ(Position position, Collection<Block> material);

	public abstract Position getBestXY(Position position, Collection<Block> material);

	public abstract Position getBestXZ(Position position, Collection<Block> material);

	public abstract Position getBestYZ(Position position, Collection<Block> material);

	public abstract Position getBestX(Position position, Collection<Block> material);

	public abstract Position getBestY(Position position, Collection<Block> material);

	public abstract Position getBestZ(Position position, Collection<Block> material);
}
