package minecraft;

public class SubBlockMaterial {

	private Position position;
	private SubBlockPosition subBlockPosition;
	private int material;

	public SubBlockMaterial(Position position, SubBlockPosition subBlockPosition, int material) {
		this.position = position;
		this.subBlockPosition = subBlockPosition;
		this.material = material;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public SubBlockPosition getSubBlockPosition() {
		return this.subBlockPosition;
	}

	public void setSubBlockPosition(SubBlockPosition subBlockPosition) {
		this.subBlockPosition = subBlockPosition;
	}

	public int getMaterial() {
		return this.material;
	}

	public void setMaterial(int material) {
		this.material = material;
	}

	public Position getPoint() {
		return new Position(this.position.getX(), this.position.getY(), this.position.getZ());
	}
}
