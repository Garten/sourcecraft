package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

/**
 * A nonsolid block (thus counts as air) that has a lowered hight when it ends
 *
 */
public class Liquid extends Addable {

	public Liquid() {
		int[] temp = { Material.WATER, Material.LAVA };
		super.materialUsedFor = temp;
	}

	@Override
	public String getName() {
		return "liquid";
	}

	@Override
	public void add(Position position, int material) {
		Position end = this.cuboidFinder.getBestXYZ(position, material);
		this.map.addSolid(this.map.createCuboid(position, end, material));
		this.map.markAsConverted(position, end);
	}
}
