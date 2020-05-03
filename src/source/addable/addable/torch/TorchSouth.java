package source.addable.addable.torch;

import minecraft.Position;
import source.Material;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchSouth extends Torch {

	public TorchSouth() {
		int[] temp = { Material.WALL_TORCH$SOUTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
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
		this.map.addSolidEntity(
				new FuncIllusionary(this.map.createFree8Point(point, point, parts, pointOffset, false, material)));
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension(0.5, 0.7, (7.0 / 20.0));
		this.addFlame();
		this.map.markAsConverted(p);
	}
}
