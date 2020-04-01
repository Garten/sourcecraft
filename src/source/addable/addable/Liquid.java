package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

/**
 * A non-solid block (thus counts as air) that has a lowered height when it ends
 *
 */
public class Liquid extends Addable {

	public Liquid() {
		super.materialUsedFor = new int[] { Material.WATER, Material.LAVA, Material.SEAGRASS, Material.TALL_SEAGRASS };
	}

	@Override
	public void add(Position position, int material) {
		Position end = this.cuboidFinder.getBestXYZ(position, material);
		this.map.addSolid(this.map.createCuboid(position, end, material));
		this.map.markAsConverted(position, end);
	}
}
