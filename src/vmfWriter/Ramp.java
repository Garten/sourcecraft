package vmfWriter;

import java.io.IOException;

import basic.Tuple;
import minecraft.Position;

public class Ramp extends Cuboid {

	private Orientation orientation;

	public Ramp(Cuboid cuboid, Orientation orientation) {
		super(cuboid);
		this.orientation = orientation;
	}

	public Ramp(Tuple<Position, Position> positions, Skin skin, Orientation o) {
		super(positions, skin);
		this.orientation = o;
	}

	public Ramp(Position fPoint, int xLength, int yLength, int zLength, int scale, Skin skin, Orientation o) {
		super(fPoint, xLength, yLength, zLength, scale);
		this.setSkin(skin);
		this.orientation = o;
		this.scale(scale);
	}

	@Override
	public void writeVmf(ValveWriter writer) throws IOException {
		writer.open(Solid.SOLID_TAG)
				.putBrushID();
		switch (this.orientation) {
		case WEST:
			this.writeSide(writer, this.h, this.d, this.b, this.skin.materialTop, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.d, this.h, this.e, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);

			this.writeSide(writer, this.b, this.a, this.e, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			// todo right side ?

			this.writeSide(writer, this.f, this.e, this.h, this.skin.materialBack, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
		case EAST:
			this.writeSide(writer, this.a, this.e, this.g, this.skin.materialTop, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.d, this.h, this.e, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);

			this.writeSide(writer, this.h, this.d, this.c, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);

			this.writeSide(writer, this.d, this.a, this.c, this.skin.materialFront, this.textureScaleY, this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
		case NORTH:
			this.writeSide(writer, this.g, this.d, this.a, this.skin.materialTop, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.d, this.h, this.e, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);

			this.writeSide(writer, this.a, this.e, this.f, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);

			this.writeSide(writer, this.d, this.g, this.h, this.skin.materialBack, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.f, this.e, this.h, this.skin.materialFront, this.textureScaleY, this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS); // really?
		case SOUTH:
			this.writeSide(writer, this.d, this.a, this.b, this.skin.materialTop, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.a, this.d, this.h, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);

			this.writeSide(writer, this.a, this.e, this.b, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);

			this.writeSide(writer, this.d, this.c, this.h, this.skin.materialBack, this.textureScaleX, this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.h, this.c, this.b, this.skin.materialFront, this.textureScaleY, this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
		}
		writer.open(ValveElement.EDITOR_TAG)
				.put(ValveElement.COLOR, "0 215 172")
				.put(ValveElement.VISGROUPSHOWN, 1)
				.put(ValveElement.VISGROUPAUTOSHOWN, 1)
				.close();
		writer.close();
	}
}
