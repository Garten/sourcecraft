package gui.panel;

import minecraft.Position;

public class LabeledCoordinates {

	private String name;

	private Position start;
	private Position end;

	public LabeledCoordinates(String name) {
		this(name, null, null);
	}

	public LabeledCoordinates(String name, Position start, Position end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Position getStart() {
		return this.start;
	}

	public Position getEnd() {
		return this.end;
	}

	public String getDebugInfo() {
		return this.name + " " + this.start.toString() + " " + this.end.toString();
	}

	public void enlarge(Position lowering, Position highering) {
		this.start = Position.add(this.start, lowering);
		this.end = Position.add(this.end, highering);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof LabeledCoordinates)) {
			return false;
		}
		return this.name.equals(((LabeledCoordinates) other).name);
	}
}
