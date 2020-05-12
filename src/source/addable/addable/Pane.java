package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import source.addable.ConvertAction;

public class Pane extends ConvertAction {

	public Pane() {
		int temp[] = { MaterialLegacy.GLASS_PANE };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position bestXY = context.getCuboidFinder()
				.getBestXY(p, material);
		Position bestYZ = context.getCuboidFinder()
				.getBestYZ(p, material);
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
			int sumX = this.countAirY(context, x + 1, y, z, bestXY.getY())
					+ this.countAirY(context, x - 1, y, z, bestXY.getY());
			int sumZ = this.countAirY(context, x, y, z + 1, bestYZ.getY())
					+ this.countAirY(context, x, y, z - 1, bestYZ.getY());
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
		context.addDetail(context.createCuboid(p, end, parts, offset, negativeOffset, material));
		context.markAsConverted(p, end);
	}

	private int countAirY(ConverterContext context, int x, int yStart, int z, int yEnd) {
		int sum = 0;
		for (int yRun = yStart; yRun <= yEnd; yRun++) {
			if (context.isAirBlock(new Position(x, yRun, z))) {
				sum++;
			}
		}
		return sum;
	}
}
