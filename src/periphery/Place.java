package periphery;

import java.util.Objects;

import minecraft.Area;
import minecraft.Position;

public class Place {

	private String displayName;
	private String world;

	private Position start;
	private Position end;

	public Place(String name) {
		this.displayName = name;
		this.start = new Position();
		this.end = new Position();
	}

	public static Place create() {
		return new Place();
	}

	public Place() {
		this.start = new Position();
		this.end = new Position();
	}

	public String getWorld() {
		return this.world;
	}

	public static String getWorld(Place place) {
		if (place == null) {
			return null;
		}
		return place.getWorld();
	}

	public Place setWorld(String world) {
		this.world = world;
		return this;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public Position getStart() {
		return this.start.copy();
	}

	public Position getEnd() {
		return this.end.copy();
	}

	public Place setStart(Position start) {
		this.start.setTo(start);
		return this;
	}

	public Place setEnd(Position end) {
		this.end.setTo(end);
		return this;
	}

	@Override
	public String toString() {
		return this.displayName;
	}

	public Area createBound() {
		return new Area(this.getStart(), this.getEnd());
	}

	public Place setTo(Place other) {
		if (other == null) {
			return this;
		}
		if (!other.displayName.isEmpty()) {
			this.displayName = other.displayName;
		}
		this.start.setTo(other.start);
		this.end.setTo(other.end);
		return this;
	}

	public boolean correctCoordinates() {
		boolean changed = Position.bringInOrder(this.start, this.end);
		if (this.start.x == this.end.x) {
			this.end.x = this.end.x + 1;
			changed = true;
		}
		if (this.start.y == this.end.y) {
			this.end.y = this.end.y + 1;
			changed = true;
		}
		if (this.start.z == this.end.z) {
			this.end.z = this.end.z + 1;
			changed = true;
		}
		return changed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.displayName, this.end, this.start, this.world);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Place)) {
			return false;
		}
		Place other = (Place) obj;
		return Objects.equals(this.displayName, other.displayName) && Objects.equals(this.end, other.end)
				&& Objects.equals(this.start, other.start) && Objects.equals(this.world, other.world);
	}

}
