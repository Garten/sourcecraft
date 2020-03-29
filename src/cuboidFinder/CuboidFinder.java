package cuboidFinder;

import minecraft.Position;

public interface CuboidFinder {

	public abstract Position getBestXYZ(Position p, int... materials);

	public abstract Position getBestXZ(Position p, int... materials);

	public abstract Position getBestXY(Position p, int... materials);

	public abstract Position getBestYZ(Position p, int... materials);

	public abstract Position getBestX(Position p, int... materials);

	public abstract Position getBestY(Position p, int... materials);

	public abstract Position getBestZ(Position p, int... materials);
}
