package minecraft;

import java.util.Objects;

import basic.Loggger;

public class Position implements Comparable<Position> {

	public int x;
	public int y;
	public int z;

	public Position() {
		this(0, 0, 0);
	}

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Position create(Position template) {
		return new Position(template.x, template.y, template.z);
	}

	@Deprecated
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	public Position(double x, double y, double z) {
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public Position setX(int x) {
		this.x = x;
		return this;
	}

	public Position setY(int y) {
		this.y = y;
		return this;
	}

	public Position setZ(int z) {
		this.z = z;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y, this.z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

	@Override
	public int compareTo(Position other) {
		int xDiff = this.x - other.x;
		if (xDiff != 0) {
			return xDiff;
		}
		int yDiff = this.y - other.y;
		if (yDiff != 0) {
			return yDiff;
		}
		int zDiff = this.z - other.z;
		return zDiff;
	}

	@Override
	public String toString() {
		return this.x + "," + this.y + "," + this.z;
	}

	public String toAxisString() {
		return this.x + " " + this.y + " " + this.z;
	}

	public String toBracketString() {
		return "[" + this.x + " " + this.y + " " + this.z + "]";
	}

	public Position move(int x, int y, int z) {
		this.x = this.x + x;
		this.y = this.y + y;
		this.z = this.z + z;
		return this;
	}

	public static Position add(Position a, Position b) {
		if (a == null || b == null) {
			return null;
		}
		return new Position(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public Position getOffset(int x, int y, int z) {
		return new Position(this.x + x, this.y + y, this.z + z);
	}

	public String getString() {
		return this.x + " " + this.y + " " + this.z;
	}

	public int getRoomSizeTo(Position target) {
		return (target.x - this.x) * (target.y - this.y) * (target.z - this.z);
	}

	public Position scale(int scale) {
		this.x = this.x * scale;
		this.y = this.y * scale;
		this.z = this.z * scale;
		return this;
	}

	public boolean smaller(Position other) {
		return this.x < other.x && this.y < other.y && this.z < other.z;
	}

	public void setTo(Position other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public Position copy() {
		return new Position(this.x, this.y, this.z);
	}

	public static Position subtract(Position position, Position end) {
		if (position == null || end == null) {
			return null;
		}
		int x = position.x - end.x;
		int y = position.y - end.y;
		int z = position.z - end.z;
		return new Position(x, y, z);
	}

	public static Position getMean(Position one, Position two) {
		Position result = new Position();
		result.setX((one.getX() + two.getX()) / 2);
		result.setY((one.getY() + two.getY()) / 2);
		result.setZ((one.getZ() + two.getZ()) / 2);
		return result;
	}

	// return if order changed
	public static boolean bringInOrder(Position start, Position end) {
		if (start == null || end == null) {
			return false;
		}
		boolean change = false;
		int firstValue = start.getX();
		int secondValue = end.getX();
		if (firstValue > secondValue) {
			start.setX(secondValue);
			end.setX(firstValue);
			change = true;
		}
		firstValue = start.getY();
		secondValue = end.getY();
		if (firstValue > secondValue) {
			start.setY(secondValue);
			end.setY(firstValue);
			change = true;
		}
		firstValue = start.getZ();
		secondValue = end.getZ();
		if (firstValue > secondValue) {
			start.setZ(secondValue);
			end.setZ(firstValue);
			change = true;
		}
		return change;
	}

	public static void addTo(Position writeTarget, Position diff) {
		writeTarget.x += diff.x;
		writeTarget.y += diff.y;
		writeTarget.z += diff.z;
	}

	public static Position areaSpan(Position lower, Position higher) {
		Position result = new Position();
		result.x = higher.x - lower.x + 1;
		result.y = higher.y - lower.y + 1;
		result.z = higher.z - lower.z + 1;
		return result;
	}

	public static Position capBy(Position position, Position cap) {
		if (position == null || cap == null) {
			Loggger.warn("Position null");
			return null;
		}
		Position target = position.copy();
		if (target.getX() > cap.getX()) {
			target.x = cap.x;
		}
		if (target.getY() > cap.getY()) {
			target.y = cap.y;
		}
		if (target.getZ() > cap.getZ()) {
			target.z = cap.z;
		}
		return target;
	}

	public static Position capBelow(Position position, Position cap) {
		if (position == null || cap == null) {
			Loggger.warn("Position null");
			return null;
		}
		Position target = position.copy();
		if (target.getX() >= cap.getX()) {
			target.x = cap.x - 1;
		}
		if (target.getY() >= cap.getY()) {
			target.y = cap.y - 1;
		}
		if (target.getZ() >= cap.getZ()) {
			target.z = cap.z - 1;
		}
		return target;
	}
}
