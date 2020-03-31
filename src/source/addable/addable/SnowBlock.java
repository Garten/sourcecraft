package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

public class SnowBlock extends Addable {

	public SnowBlock() {
		int[] temp = { Material.SNOW };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXZ(p, material);
		int parts = 8;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 7, 0);
		this.map.addDetail(this.map.createCuboid(p, end, parts, offset, negativeOffset, material));
		this.map.markAsConverted(p, end);
	}
}
