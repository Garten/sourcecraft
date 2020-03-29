package source.addable.addable.torch;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class TorchEast extends Addable {

	public static int red = 255;
	public static int blue = 50;
	public static int green = 243;
	public static int brigthness = 40;
	public final static int distance50 = 96;
	public final static int distance100 = 256;

	public TorchEast() {
		int[] temp = { Material.WALL_TORCH_EAST };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public String getName() {
		return "torchEast";
	}

	@Override
	public void add(Position p, int material) {
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
		this.map.addSolidEntity(new FuncIllusionary(this.map.createFree8Point(point, point, parts, pointOffset, true, material)));
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension((7.0 / 20.0), 0.7, 0.5);
		this.map.addPointEntity(Torch.PARTICLE_SYSTEM);
		this.map.movePointInGridDimension(0, 1.0 / ((parts)), 0);
		this.map.addPointEntity(Torch.LIGHT);
		this.map.markAsConverted(p);
	}
}
