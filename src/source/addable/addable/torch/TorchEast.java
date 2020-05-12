package source.addable.addable.torch;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.MaterialLegacy;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchEast extends Torch {

	public static int red = 255;
	public static int blue = 50;
	public static int green = 243;
	public static int brigthness = 40;
	public final static int distance50 = 96;
	public final static int distance100 = 256;

	public TorchEast() {
		int[] temp = { MaterialLegacy.WALL_TORCH$EAST };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		int parts = 32;
		Position[] pointOffset = new Position[8];
		pointOffset[0] = new Position(0, 6, 18); // a
		pointOffset[1] = new Position(9, 24, 18); // b
		pointOffset[2] = new Position(13, 22, 18); // c
		pointOffset[3] = new Position(4, 4, 18); // d
		pointOffset[4] = new Position(0, 6, 14); // e
		pointOffset[5] = new Position(9, 24, 14); // f
		pointOffset[6] = new Position(13, 22, 14); // g
		pointOffset[7] = new Position(4, 4, 14); // h

		Position point = new Position(p);
		context.addSolidEntity(
				new FuncIllusionary(context.createFree8Point(point, point, parts, pointOffset, true, material)));
		context.setPointToGrid(p);
		context.movePointInGridDimension((7.0 / 20.0), 0.7, 0.5);
		this.addFlame(context);
		context.markAsConverted(p);

//		textureshift: -92
//		textureshift -24
//		rotation 28
	}
}
