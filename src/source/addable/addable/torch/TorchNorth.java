package source.addable.addable.torch;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchNorth extends Addable {

	public TorchNorth() {
		int[] temp = { Material.WALL_TORCH_NORTH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public String getName() {
		return "torchNorth";
	}

	@Override
	public void add(Position p, int material) {
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
		this.map.addSolidEntity(new FuncIllusionary(this.map.createFree8Point(point, point, parts, pointOffset, false, material)));
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension(0.5, 0.7, (13.0 / 20.0));
		this.map.addPointEntity(Torch.PARTICLE_SYSTEM);
		this.map.movePointInGridDimension(0, 1.0 / ((parts)), 0);
		this.map.addPointEntity(Torch.LIGHT);
		this.map.markAsConverted(p);
	}
}
