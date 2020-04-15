package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

/**
 * normal block but counts as air
 */
public class TransparentBlock extends Addable {

	public TransparentBlock() {
		int[] temp = { Material.OAK_LEAVES, Material.GLASS, Material.ICE, Material.JUNGLE_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXYZ(p, material);
		this.map.addSolid(this.map.createCuboid(p, end, material));
		this.map.markAsConverted(p, end);
	}
}
