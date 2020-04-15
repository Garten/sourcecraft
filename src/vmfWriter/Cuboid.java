package vmfWriter;

import java.io.IOException;

import basic.Loggger;
import basic.Tuple;
import minecraft.Position;

public class Cuboid extends EightPoint {

	public Cuboid(Cuboid other) {
		this.skin = other.skin; // TODO copy skin
		this.a = other.a;
		this.b = other.b;
		this.c = other.c;
		this.d = other.d;
		this.e = other.e;
		this.f = other.f;
		this.g = other.g;
		this.h = other.h;
		this.textureScaleX = other.textureScaleX;
		this.textureScaleY = other.textureScaleY;
		this.textureScaleZ = other.textureScaleZ;
	}

	public Cuboid(Position[] p, Skin skin) {
		this.skin = skin;
		if (p.length < 8) {
			if (p.length < 1) {
				Loggger.warn("Less than 8 Points given.");
			} else {
				Loggger.warn("Less than 8 Points given. Create a cuboid with the first given point.");
			}
		} else {
			this.a = p[0];
			this.b = p[1];
			this.c = p[2];
			this.d = p[3];
			this.e = p[4];
			this.f = p[5];
			this.g = p[6];
			this.h = p[7];
		}
	}

	public Cuboid(Tuple<Position, Position> positions, Skin skin) {
		this(positions);
		this.setSkin(skin);
	}

	public Cuboid(Tuple<Position, Position> positions) {
		Position aPoint = positions.getFirst();
		Position gPoint = positions.getSecond();
		if (aPoint.smaller(gPoint) == false) {
			Loggger.warn("Attampted to create invalid Cuboid.");
			Loggger.warn(" from " + aPoint.getString() + " to " + gPoint.getString());
			gPoint = aPoint.getOffset(1, 1, 1);
			this.skin = new Skin("Attempted to creat invalid Cuboid", 0.25);
		}
		int xOffset = gPoint.x - aPoint.x;
		int yOffset = gPoint.y - aPoint.y;
		int zOffset = gPoint.z - aPoint.z;
		this.a = new Position(aPoint);
		this.e = this.a.getOffset(0, yOffset, 0);
		this.d = this.a.getOffset(xOffset, 0, 0);
		this.h = this.a.getOffset(xOffset, yOffset, 0);

		this.b = this.a.getOffset(0, 0, zOffset);
		this.f = this.e.getOffset(0, 0, zOffset);
		this.c = this.d.getOffset(0, 0, zOffset);
		this.g = this.h.getOffset(0, 0, zOffset);
	}

	public Cuboid(Position fPoint, int xLength, int yLength, int zLength, int scale) {
		if (xLength <= 0 && yLength <= 0 && zLength <= 0) {
			Loggger.warn("Attampted to create invalid Cuboid.");
			xLength = -xLength + 1;
			yLength = -yLength + 1;
			zLength = -zLength + 1;
			this.skin = new Skin("Attempted to creat invalid Cuboid", 0.25);
		}
		this.f = new Position(fPoint);
		this.g = new Position(this.f.x + xLength, this.f.y, this.f.z);
		this.b = new Position(this.f.x, this.f.y - yLength, this.f.z);
		this.c = new Position(this.f.x + xLength, this.f.y - yLength, this.f.z);
		this.e = new Position(this.f.x, this.f.y, this.f.z - zLength);
		this.h = new Position(this.g.x, this.g.y, this.g.z - zLength);
		this.a = new Position(this.b.x, this.b.y, this.b.z - zLength);
		this.d = new Position(this.c.x, this.c.y, this.c.z - zLength);
		this.scale(scale);
	}

	public void setTextureScale(double[] textureScale) {
		this.textureScaleX = textureScale[0];
		this.textureScaleY = textureScale[1];
		this.textureScaleZ = textureScale[2];
	}

	public void cut(Orientation cut) {
		int xLength = this.g.x - this.a.x;
		int yLength = this.g.y - this.a.y;
		// int zLength = g.y - a.y;
		if (cut != null) {
			switch (cut) {
			case NORTH:
				this.b.y = this.b.y + xLength;
				this.c.y = this.c.y + xLength;
				break;
			case SOUTH:
				this.f.y = this.f.y - xLength;
				this.g.y = this.g.y - xLength;
				break;
			case EAST:
				this.f.x = this.f.x + yLength;
				this.b.x = this.b.x + yLength;
				break;
			case WEST:
				this.c.x = this.c.x - yLength;
				this.g.x = this.g.x - yLength;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void writeVmf(ValveWriter writer) throws IOException {
		if (this.a != null) {
			writer.open(Solid.SOLID_TAG)
					.putBrushID();

//			top
			this.writeSide(writer, this.b, this.f, this.g, this.skin.materialTop, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
//			bottom
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, BOTTOM_U_AXIS, BOTTOM_V_AXIS);

//			negative x side 
			this.writeSide(writer, this.a, this.e, this.f, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
//			positive x side
			this.writeSide(writer, this.h, this.d, this.c, this.skin.materialRight, this.textureScaleX, this.textureScaleZ, RIGHT_U_AXIS, RIGHT_V_AXIS);

//			positive y side
			this.writeSide(writer, this.e, this.h, this.g, this.skin.materialBack, this.textureScaleY, this.textureScaleZ, BACK_U_AXIS, BACK_V_AXIS);
//			negative y side
			this.writeSide(writer, this.d, this.a, this.b, this.skin.materialFront, this.textureScaleY, this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);

			writer.open(ValveElement.EDITOR_TAG)
					.put(ValveElement.COLOR, "0 215 172")
					.put(ValveElement.VISGROUPSHOWN, 1)
					.put(ValveElement.VISGROUPAUTOSHOWN, 1)
					.close();
			writer.close();
		} else {
			throw new IllegalArgumentException("Attempted to write Cuboid without parameters set.");
		}
	}

}
