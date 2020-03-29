package vmfWriter;

import minecraft.Position;

public class Angles extends Position {

	public Angles() {
		this(0, 0, 0);
	}

	public Angles(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public Angles copy() {
		return new Angles(this.x, this.y, this.z);
	}
}
