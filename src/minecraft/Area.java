package minecraft;

import java.util.Iterator;

public class Area implements Iterable<Position> {

	private Position lower;
	private Position higher;

	public Area(Position lower, Position higher) {
		this.lower = Position.create(lower);
		this.higher = Position.create(higher);
	}

	public Position getLower() {
		return this.lower;
	}

	public Area setLower(Position lower) {
		this.lower = lower;
		return this;
	}

	public Position getHigher() {
		return this.higher;
	}

	public Area setHigher(Position higher) {
		this.higher = higher;
		return this;
	}

	@Override
	public Iterator<Position> iterator() {
		return new Iterator<Position>() {

			Position current = Position.create(Area.this.lower);

			@Override
			public boolean hasNext() {
				return (this.current != null && this.current.x <= Area.this.higher.x
						&& this.current.y <= Area.this.higher.y && this.current.z <= Area.this.higher.z);
			}

			@Override
			public Position next() {
				if (this.current == null) {
					return null;
				}
				Position result = Position.create(this.current);
				if (this.current.x < Area.this.higher.x) {
					this.current.x++;
				} else if (this.current.y < Area.this.higher.y) {
					this.current.y++;
					this.current.x = Area.this.lower.x;
				} else if (this.current.z < Area.this.higher.z) {
					this.current.z++;
					this.current.x = Area.this.lower.x;
					this.current.y = Area.this.lower.y;
				} else {
					this.current = null;
				}
				return result;
			}

		};
	}
}
