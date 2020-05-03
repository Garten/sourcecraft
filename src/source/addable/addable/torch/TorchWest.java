package source.addable.addable.torch;

import minecraft.Position;
import source.Material;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchWest extends Torch {

	public TorchWest() {
		int[] temp = { Material.WALL_TORCH$WEST };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		int parts = 32;
		Position[] pointOffset = new Position[8];

		pointOffset[0] = new Position(32, 6, 14); // a
		pointOffset[1] = new Position(23, 24, 14); // b
		pointOffset[2] = new Position(19, 22, 14); // c
		pointOffset[3] = new Position(28, 4, 14); // d
		pointOffset[4] = new Position(32, 6, 18); // e
		pointOffset[5] = new Position(23, 24, 18); // f
		pointOffset[6] = new Position(19, 22, 18); // g
		pointOffset[7] = new Position(28, 4, 18); // h

		Position point = new Position(p);
		this.map.addSolidEntity(
				new FuncIllusionary(this.map.createFree8Point(point, point, parts, pointOffset, true, material)));
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension((13.0 / 20.0), 0.7, 0.5);
		this.addFlame();
		this.map.markAsConverted(p);
	}
}
