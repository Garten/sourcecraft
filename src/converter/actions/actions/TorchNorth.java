package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchNorth extends Action {

	public TorchNorth() {
		int[] temp = { MaterialLegacy.WALL_TORCH$NORTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		int parts = 32;
		Position[] pointOffset = new Position[8];

		pointOffset[0] = new Position(18, 6, 32); // a
		pointOffset[1] = new Position(18, 24, 23); // b
		pointOffset[2] = new Position(18, 22, 19); // c
		pointOffset[3] = new Position(18, 4, 28); // d
		pointOffset[4] = new Position(14, 6, 32); // e
		pointOffset[5] = new Position(14, 24, 23); // f
		pointOffset[6] = new Position(14, 22, 19); // g
		pointOffset[7] = new Position(14, 4, 28); // h

		Position point = new Position(p);
		context.addSolidEntity(
				new FuncIllusionary(context.createFree8Point(point, point, parts, pointOffset, false, material)));
		context.setPointToGrid(p);
		context.movePointInGridDimension(0.5, 0.7, (13.0 / 20.0));
		context.addPointEntity(Torch.PARTICLE_SYSTEM);
		context.movePointInGridDimension(0, 1.0 / ((parts)), 0);
		context.addPointEntity(Torch.LIGHT);
		context.markAsConverted(p);
	}
}
