package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class CenteredPointEntity extends Action {

	private RotateablePointEntity entity;
	private float yOffset = 0;

	public CenteredPointEntity() {

	}

	public CenteredPointEntity(String name) {
		this.setEntity(new RotateablePointEntity().setName(name));
	}

	public CenteredPointEntity setYOffset(float yOffset) {
		this.yOffset = yOffset;
		return this;
	}

	@Override
	public void add(Mapper context, Position position, Block material) {
		context.setPointToGrid(position);
		context.movePointInGridDimension(0.5, yOffset, 0.5);
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

	@Override
	public boolean isAirBlock() {
		return true;
	}
}
