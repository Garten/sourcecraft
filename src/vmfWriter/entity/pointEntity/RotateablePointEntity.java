package vmfWriter.entity.pointEntity;

public abstract class RotateablePointEntity extends PointEntity {

	private int rotation;

	public RotateablePointEntity setRotation(int rotation) {
		this.rotation = rotation;
		return this;
	}

	protected int getRotation() {
		return this.rotation;
	}
}
