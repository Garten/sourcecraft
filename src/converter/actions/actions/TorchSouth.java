package converter.actions.actions;

import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchSouth extends Torch {

	public TorchSouth() {
		int[] temp = { MaterialLegacy.WALL_TORCH$SOUTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		int parts = 32;
		Position[] pointOffset = new Position[8];

		pointOffset[0] = new Position(14, 6, 0); // a
		pointOffset[1] = new Position(14, 24, 9); // b
		pointOffset[2] = new Position(14, 22, 13); // c
		pointOffset[3] = new Position(14, 4, 4); // d
		pointOffset[4] = new Position(18, 6, 0); // e
		pointOffset[5] = new Position(18, 24, 9); // f
		pointOffset[6] = new Position(18, 22, 13); // g
		pointOffset[7] = new Position(18, 4, 4); // h

		Position point = new Position(p);
		context.addSolidEntity(
				new FuncIllusionary(context.createFree8Point(point, point, parts, pointOffset, false, material)));
		context.setPointToGrid(p);
		context.movePointInGridDimension(0.5, 0.7, (7.0 / 20.0));
		this.addFlame(context);
		context.markAsConverted(p);
	}
}
