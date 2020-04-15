package source.addable.addable.tf2;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.entity.pointEntity.pointEntity.PropStatic;

/**
 *
 *
 */
public class LilypadTf2 extends Addable {

	private final static String MODEL = "models/props_swamp/lilypad_large.mdl";
	private final static PropStatic LILY_PAD = new PropStatic(LilypadTf2.MODEL);

	public LilypadTf2() {
		int[] temp = { Material.LILY_PAD };
		super.materialUsedFor = temp;
	}

	@Override
	public void add(Position position, int material) {
		this.map.setPointToGrid(position);
		this.map.movePointInGridDimension(0.5, 0, 0.5);
		int verticalAngle = (int) (Math.random() * 360);
		LilypadTf2.LILY_PAD.getAngles()
				.setY(verticalAngle);
		this.map.addPointEntity(LilypadTf2.LILY_PAD);
		this.map.markAsConverted(position);
	}

}
