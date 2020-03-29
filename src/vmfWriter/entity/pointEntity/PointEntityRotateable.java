package vmfWriter.entity.pointEntity;

public abstract class PointEntityRotateable extends PointEntity {

	private int rotation;

	public PointEntityRotateable(int rotation) {
		this.rotation = rotation;
	}

	protected int getRotation() {
		return this.rotation;
	}
}
