package cuboidFinder;

import java.util.Collection;

import minecraft.Position;

public abstract class CuboidFinder {

	public abstract Position getBestXYZ(Position position, int... material);

	public abstract Position getBestXZ(Position position, int... material);

	public abstract Position getBestXY(Position position, int... material);

	public abstract Position getBestYZ(Position position, int... material);

	public abstract Position getBestX(Position position, int... material);

	public abstract Position getBestY(Position position, int... material);

	public abstract Position getBestZ(Position position, int... material);

	public Position getBestZ(Position position, Collection<Integer> materials) {
		// temp
		return this.getBestZ(position, materials.stream()
				.mapToInt(i -> i)
				.toArray());
	}

	public Position getBestX(Position position, Collection<Integer> materials) {
		// temp
		return this.getBestX(position, materials.stream()
				.mapToInt(i -> i)
				.toArray());
	}
}
