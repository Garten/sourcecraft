package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

public class Pane extends Addable {

	public Pane() {
		int temp[] = { Material.GLASS_PANE };
		super.materialUsedFor = temp;
	}

	@Override
	public String getName() {
		return "pane";
	}

	@Override
	public void add(Position p, int material) {
		Position bestXY = this.cuboidFinder.getBestXY(p, material);
		Position bestYZ = this.cuboidFinder.getBestYZ(p, material);
		int sizeXY = p.getRoomSizeTo(bestXY);
		int sizeYZ = p.getRoomSizeTo(bestYZ);
		int parts = 16;
		int yOffset = 0;
		int yStop = 0;
		int xOffset = 0;
		int zOffset = 0;
		int xStop = 0;
		int zStop = 0;
		Position end;
		if (sizeXY > sizeYZ) { // window in x,y direction
			end = bestXY;
			zOffset = 7;
			zStop = 7;
		} else if (sizeXY < sizeYZ) { // window in z,y direction
			end = bestYZ;
			xOffset = 7;
			xStop = 7;
		} else { // check for borders
			int x = p.getX();
			int y = p.getY();
			int z = p.getZ();
			int sumX = this.countAirY(x + 1, y, z, bestXY.getY()) + this.countAirY(x - 1, y, z, bestXY.getY());
			int sumZ = this.countAirY(x, y, z + 1, bestYZ.getY()) + this.countAirY(x, y, z - 1, bestYZ.getY());
			if (sumX <= sumZ) {
				end = bestXY;
				zOffset = 7;
				zStop = 7;
			} else {
				end = bestYZ;
				xOffset = 7;
				xStop = 7;
			}
		}
		Position offset = new Position(xOffset, yOffset, zOffset);
		Position negativeOffset = new Position(xStop, yStop, zStop);
		this.map.addDetail(this.map.createCuboid(p, end, parts, offset, negativeOffset, material));
		this.map.markAsConverted(p, end);
	}

	private int countAirY(int x, int yStart, int z, int yEnd) {
		int sum = 0;
		for (int yRun = yStart; yRun <= yEnd; yRun++) {
			if (this.map.isAirBlock(new Position(x, yRun, z))) {
				sum++;
			}
		}
		return sum;
	}
}
