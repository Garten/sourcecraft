package source.addable.addable;

import minecraft.Position;
import source.addable.Addable;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class SimplePointEntity extends Addable {

	private RotateablePointEntity entity;
	private int yOffset;

	public SimplePointEntity(int material) {
		super.setMaterialUsedFor(material);
	}

	@Override
	public void add(Position position, int material) {
		this.map.setPointToGrid(position);
		this.map.movePointInGridDimension(0.5, 0, 0.5);
		int verticalAngle = (int) (Math.random() * 360);
		this.map.addPointEntity(this.getEntity()
				.setRotation(verticalAngle));
		this.map.markAsConverted(position);
	}

	public SimplePointEntity setEntity(RotateablePointEntity entity) {
		this.entity = entity;
		return this;
	}

	protected RotateablePointEntity getEntity() {
		return this.entity;
	}
}
