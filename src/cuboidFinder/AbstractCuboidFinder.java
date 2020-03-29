package cuboidFinder;

import minecraft.Position;
import minecraft.map.DefaultMinecraftMapConverter;

public abstract class AbstractCuboidFinder implements CuboidFinder {

	protected final DefaultMinecraftMapConverter map;

	public AbstractCuboidFinder(DefaultMinecraftMapConverter map) {
		this.map = map;
	}

	@Override
	public Position getBestXYZ(Position p, int... materials) {
		return this.getBestXYZ(p.getX(), p.getY(), p.getZ(), materials);
	}

	@Override
	public Position getBestXY(Position p, int... materials) {
		return this.getBestXY(p.getX(), p.getY(), p.getZ(), materials);
	}

	@Override
	public Position getBestXZ(Position p, int... materials) {
		return this.getBestXZ(p.getX(), p.getY(), p.getZ(), materials);
	}

	@Override
	public Position getBestYZ(Position p, int... materials) {
		return this.getBestYZ(p.getX(), p.getY(), p.getZ(), materials);
	}

	@Override
	public Position getBestX(Position p, int... materials) {
		return this.getBestX(p.getX(), p.getY(), p.getZ(), materials);
	}

	@Override
	public Position getBestY(Position p, int... materials) {
		return this.getBestY(p.getX(), p.getY(), p.getZ(), materials);
	}

	@Override
	public Position getBestZ(Position p, int... materials) {
		return this.getBestZ(p.getX(), p.getY(), p.getZ(), materials);
	}

	// methods that determine a maximal size of a Cuboid
	public Position getBestXYZ(int x, int y, int z, int... materials) {
		int xRun = x;
		int yRun = y;
		int zRun = z;
		int xBest = x;
		int yBest = y;
		int zBest = z;
		int sizeNew = 1;
		int sizeBest = 1;

		xRun = x; // start with xRun=x so z+1 is tested first
		while (this.addXValid(x, y, z, xRun, yRun, zRun, materials)) {
			sizeNew = (xRun - x + 1) * (yRun - y + 1) * (zRun - z + 1);
			if (sizeNew > sizeBest) {
				sizeBest = sizeNew;
				xBest = xRun;
				yBest = yRun;
				zBest = zRun;
			}
			yRun = y; // start with yRun=y so z+1 is tested first
			while (this.addYValid(x, y, z, xRun, yRun, zRun, materials)) {
				sizeNew = (xRun - x + 1) * (yRun - y + 1) * (zRun - z + 1);
				if (sizeNew > sizeBest) {
					sizeBest = sizeNew;
					xBest = xRun;
					yBest = yRun;
					zBest = zRun;
				}
				zRun = z + 1;
				while (this.addZValid(x, y, z, xRun, yRun, zRun, materials)) {
					sizeNew = (xRun - x + 1) * (yRun - y + 1) * (zRun - z + 1);
					if (sizeNew > sizeBest) {
						sizeBest = sizeNew;
						xBest = xRun;
						yBest = yRun;
						zBest = zRun;
					}
					zRun++;
				}
				zRun = z;
				yRun++;
			}
			yRun = y;
			xRun++;
		}
		return new Position(xBest, yBest, zBest);
	}

	public Position getBestXZ(int x, int y, int z, int... materials) {
		int xRun = x;
		int zRun = z;
		int xBest = x;
		int zBest = z;
		int sizeNew = 1;
		int sizeBest = 1;

		xRun = x; // start with xRun=x so z+1 is tested first
		while (this.addXValid(x, y, z, xRun, y, zRun, materials)) {
			sizeNew = (xRun - x + 1) * 1 * (zRun - z + 1);
			if (sizeNew > sizeBest) {
				sizeBest = sizeNew;
				xBest = xRun;
				zBest = zRun;
			}
			zRun = z + 1;
			while (this.addZValid(x, y, z, xRun, y, zRun, materials)) {
				sizeNew = (xRun - x + 1) * 1 * (zRun - z + 1);
				if (sizeNew > sizeBest) {
					sizeBest = sizeNew;
					xBest = xRun;
					zBest = zRun;
				}
				zRun++;
			}
			zRun = z;
			xRun++;
		}
		return new Position(xBest, y, zBest);
	}

	public Position getBestXY(int x, int y, int z, int... materials) {
		int xRun = x;
		int yRun = y;
		int xBest = x;
		int yBest = y;
		int sizeNew = 1;
		int sizeBest = 1;

		xRun = x; // start with xRun=x so z+1 is tested first
		while (this.addXValid(x, y, z, xRun, yRun, z, materials)) {
			sizeNew = (xRun - x + 1) * 1 * (yRun - y + 1);
			if (sizeNew > sizeBest) {
				sizeBest = sizeNew;
				xBest = xRun;
				yBest = yRun;
			}
			yRun = y + 1;
			while (this.addYValid(x, y, z, xRun, yRun, z, materials)) {
				sizeNew = (xRun - x + 1) * 1 * (yRun - y + 1);
				if (sizeNew > sizeBest) {
					sizeBest = sizeNew;
					xBest = xRun;
					yBest = yRun;
				}
				yRun++;
			}
			yRun = y;
			xRun++;
		}
		return new Position(xBest, yBest, z);
	}

	public Position getBestYZ(int x, int y, int z, int... materials) {
		int zRun = z;
		int yRun = y;
		int zBest = z;
		int yBest = y;
		int sizeNew = 1;
		int sizeBest = 1;

		zRun = z; // start with xRun=x so z+1 is tested first
		while (this.addYValid(x, y, z, x, yRun, zRun, materials)) {
			sizeNew = (zRun - z + 1) * 1 * (yRun - y + 1);
			if (sizeNew > sizeBest) {
				sizeBest = sizeNew;
				zBest = zRun;
				yBest = yRun;
			}
			yRun = y + 1;
			while (this.addZValid(x, y, z, x, yRun, zRun, materials)) {
				sizeNew = (zRun - z + 1) * 1 * (yRun - y + 1);
				if (sizeNew > sizeBest) {
					sizeBest = sizeNew;
					zBest = zRun;
					yBest = yRun;
				}
				yRun++;
			}
			yRun = y;
			zRun++;
		}
		return new Position(x, yBest, zBest);
	}

	public Position getBestX(int x, int y, int z, int... material) {
		int xRun = x;
		while (this.addXValid(x, y, z, xRun, y, z, material)) {
			xRun++;
		}
		// int sizeBest = (xRun - x);
		return new Position(xRun - 1, y, z);
	}

	public Position getBestY(int x, int y, int z, int... materials) {
		int yRun = y;
		while (this.addYValid(x, y, z, x, yRun, z, materials)) {
			yRun++;
		}
		// int sizeBest = (yRun - y);
		return new Position(x, yRun - 1, z);
	}

	public Position getBestZ(int x, int y, int z, int... materials) {
		int zRun = z;
		while (this.addZValid(x, y, z, x, y, zRun, materials)) {
			zRun++;
		}
		// int sizeBest = (zRun - z);
		assert (zRun - 1) >= z;
		return new Position(x, y, zRun - 1);
	}

	private boolean addZValid(int x, int y, int z, int xRun, int yRun, int zRun, int... materials) {
		for (int xTest = x; xTest <= xRun; xTest++) {
			for (int yTest = y; yTest <= yRun; yTest++) {
				if (this.blockNotValid(xTest, yTest, zRun, materials)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean addYValid(int x, int y, int z, int xRun, int yRun, int zRun, int... materials) {
		for (int xTest = x; xTest <= xRun; xTest++) {
			for (int zTest = z; zTest <= zRun; zTest++) {
				if (this.blockNotValid(xTest, yRun, zTest, materials)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean addXValid(int x, int y, int z, int xRun, int yRun, int zRun, int... materials) {
		for (int yTest = y; yTest <= yRun; yTest++) {
			for (int zTest = z; zTest <= zRun; zTest++) {
				if (this.blockNotValid(xRun, yTest, zTest, materials)) {
					return false;
				}
			}
		}
		return true;
	}

	protected abstract boolean blockNotValid(int xTest, int yTest, int zTest, int... material);
}
