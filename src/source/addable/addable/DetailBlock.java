package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.solidEntity.FuncDetail;

/**
 * creates a "func_detail"-Block
 */
public class DetailBlock extends Addable {

	public DetailBlock() {
		int[] temp = { Material.OAK_LEAVES, Material.GLASS, Material.ICE, Material.JUNGLE_LEAVES,
				Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES, Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestXYZ(p, material);
		this.map.addSolidEntity(new FuncDetail(this.map.createCuboid(p, end, material)));
		this.map.markAsConverted(p, end);
	}
}
