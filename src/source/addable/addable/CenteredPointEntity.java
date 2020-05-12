package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.addable.ConvertAction;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class CenteredPointEntity extends ConvertAction {

	private RotateablePointEntity entity;
	private int yOffset;

	public CenteredPointEntity() {

	}

	public CenteredPointEntity(String name) {
		this.setEntity(new RotateablePointEntity().setName(name));
	}

	@Override
	public void add(ConverterContext context, Position position, Block material) {
		context.setPointToGrid(position);
		context.movePointInGridDimension(0.5, 0, 0.5);
		int verticalAngle = (int) (Math.random() * 360);
		context.addPointEntity(this.getEntity()
				.setRotation(verticalAngle));
		context.markAsConverted(position);
	}

	public CenteredPointEntity setEntity(RotateablePointEntity entity) {
		this.entity = entity;
		return this;
	}

	protected RotateablePointEntity getEntity() {
		return this.entity;
	}
}
