package source.addable.addable.tf2;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 *
 *
 */
public class TallGrassTf2 extends Addable {

	private final static String MODEL = "models/props_swamp/tallgrass_01.mdl";
	private final static PropStatic TALL_GRASS = new PropStatic(TallGrassTf2.MODEL);

	public TallGrassTf2() {
		int[] temp = { Material.GRASS };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension(0.5, 0, 0.5);
		int verticalRotation = (int) (Math.random() * 360);
		TallGrassTf2.TALL_GRASS.getAngles()
				.setY(verticalRotation);
		this.map.addPointEntity(TallGrassTf2.TALL_GRASS);
		this.map.markAsConverted(p);
	}

}
