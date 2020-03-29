package minecraft;

/**
 *
 *
 */
public class SubBlock implements Comparable<SubBlock> {
	private Position position;
	private SubBlockPosition subBlockPosition;

	public SubBlock(Position position, SubBlockPosition subBlockPosition) {
		this.position = position;
		this.subBlockPosition = subBlockPosition;
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

	@Override
	public int compareTo(SubBlock object) {
		SubBlock other = object;
		int positionResult = this.position.compareTo(other.position);
		if (positionResult == 0) {
			return this.subBlockPosition.compareTo(other.subBlockPosition);
		}
		return positionResult;
	}

}
