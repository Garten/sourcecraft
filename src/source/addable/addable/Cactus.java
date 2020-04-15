package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

public class Cactus extends Addable {

	public Cactus() {
		int[] temp = { Material.CACTUS };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestY(p, material);
		int parts = 8;
		Position offset = new Position(1, 0, 1);
		Position negativeOffset = new Position(1, 1, 1);
		this.map.addDetail(this.map.createCuboid(p, end, parts, offset, negativeOffset, material));
		this.map.markAsConverted(p, end);
	}
}
