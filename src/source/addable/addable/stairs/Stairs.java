package source.addable.addable.stairs;

import minecraft.OrientationStairs;
import minecraft.Position;
import minecraft.SubBlockPosition;
import source.addable.Addable;

/**
 *
 *
 */
public abstract class Stairs extends Addable {

	protected int materialReplacement;
	protected boolean[] subField = new boolean[8];

	protected final void clearSubField() {
		for (int i = 0; i < 8; i++) {
			this.subField[i] = false;
		}
	}

	@Override
	public final void add(Position position, int material) {
		this.map.markAsConverted(position);
		this.clearSubField();
		this.getType(position);
		for (SubBlockPosition subBlockPosition : SubBlockPosition.values()) {
			if (this.subField[subBlockPosition.ordinal()] == true) {
				this.map.addSubBlock(position, subBlockPosition, material); // this.materialReplacement
			}
		}
		this.addClipRamp(position);
	}

	abstract void getType(Position p);

	protected void setSubBlock(SubBlockPosition position) {
		assert this.subField[SubBlockPosition.getPos(position)] == false : "position = " + position;
		this.subField[SubBlockPosition.getPos(position)] = true;
	}

	private void addClipRamp(Position position, OrientationStairs orientation) {
//		this.map.addMaterialForRerun(position, orientation.getMaterial());
	}

	protected final void addClipRamp(Position position) {
//		this.map.enableRerun(1);
		if (this.subField[SubBlockPosition.TOP_EAST_SOUTH.ordinal()]) {
			if (this.subField[SubBlockPosition.TOP_EAST_NORTH.ordinal()]) {
				if (this.subField[SubBlockPosition.TOP_WEST_NORTH.ordinal()]) {
					this.addClipRamp(position, OrientationStairs.BIG_EAST_NORTH);
				} else if (this.subField[SubBlockPosition.TOP_WEST_SOUTH.ordinal()]) {
					this.addClipRamp(position, OrientationStairs.BIG_EAST_SOUTH);
				} else {
					this.addClipRamp(position, OrientationStairs.EAST);
				}
			} else if (this.subField[SubBlockPosition.TOP_WEST_SOUTH.ordinal()]) {
				if (this.subField[SubBlockPosition.TOP_WEST_NORTH.ordinal()]) {
					this.addClipRamp(position, OrientationStairs.BIG_WEST_SOUTH);
				} else {
					this.addClipRamp(position, OrientationStairs.SOUTH);
				}
			} else {
				this.addClipRamp(position, OrientationStairs.SMALL_EAST_SOUTH);
			}
		} else if (this.subField[SubBlockPosition.TOP_WEST_SOUTH.ordinal()]) {
			if (this.subField[SubBlockPosition.TOP_WEST_NORTH.ordinal()]) {
				if (this.subField[SubBlockPosition.TOP_EAST_NORTH.ordinal()]) {
					this.addClipRamp(position, OrientationStairs.BIG_WEST_NORTH);
				} else {
					this.addClipRamp(position, OrientationStairs.WEST);
				}
			} else {
				this.addClipRamp(position, OrientationStairs.SMALL_WEST_SOUTH);
			}
		} else if (this.subField[SubBlockPosition.TOP_WEST_NORTH.ordinal()]) {
			if (this.subField[SubBlockPosition.TOP_EAST_NORTH.ordinal()]) {
				this.addClipRamp(position, OrientationStairs.NORTH);
			} else {
				this.addClipRamp(position, OrientationStairs.SMALL_WEST_NORTH);
			}
		} else {
			this.addClipRamp(position, OrientationStairs.SMALL_EAST_NORTH);
		}

	}
}
