package vmfWriter;

import java.io.IOException;

import basic.Tuple;
import converter.Orientation;
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
			// top
			this.writeSide(writer, this.aTop, this.eTop, this.h, this.skin.materialTop, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
//			bottom
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX,
					this.textureScaleY, BOTTOM_U_AXIS, BOTTOM_V_AXIS);

//			negative x side
			this.writeSide(writer, this.a, this.e, this.eTop, this.skin.materialLeft, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
//			positive y side
			this.writeSide(writer, this.e, this.h, this.eTop, this.skin.materialBack, this.textureScaleY,
					this.textureScaleZ, BACK_U_AXIS, BACK_V_AXIS);
//			negative y side
			this.writeSide(writer, this.d, this.a, this.aTop, this.skin.materialFront, this.textureScaleY,
					this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
			break;
		case EAST:
//			top
			this.writeSide(writer, this.a, this.e, this.hTop, this.skin.materialTop, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
//			bottom
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX,
					this.textureScaleY, BOTTOM_U_AXIS, BOTTOM_V_AXIS);

//			positive x side
			this.writeSide(writer, this.h, this.d, this.dTop, this.skin.materialRight, this.textureScaleX,
					this.textureScaleZ, RIGHT_U_AXIS, RIGHT_V_AXIS);

//			positive y side
			this.writeSide(writer, this.e, this.h, this.hTop, this.skin.materialBack, this.textureScaleY,
					this.textureScaleZ, BACK_U_AXIS, BACK_V_AXIS);
//			negative y side
			this.writeSide(writer, this.d, this.a, this.dTop, this.skin.materialFront, this.textureScaleY,
					this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
			break;
		case NORTH:
			this.writeSide(writer, this.hTop, this.d, this.a, this.skin.materialTop, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.d, this.h, this.e, this.skin.materialBottom, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);

			this.writeSide(writer, this.a, this.e, this.eTop, this.skin.materialLeft, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);

			this.writeSide(writer, this.d, this.hTop, this.h, this.skin.materialBack, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.eTop, this.e, this.h, this.skin.materialFront, this.textureScaleY,
					this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS); // really?
			break;
		case SOUTH:
			this.writeSide(writer, this.d, this.a, this.aTop, this.skin.materialTop, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.a, this.d, this.h, this.skin.materialBottom, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);

			this.writeSide(writer, this.a, this.e, this.aTop, this.skin.materialLeft, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);

			this.writeSide(writer, this.d, this.dTop, this.h, this.skin.materialBack, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.h, this.dTop, this.aTop, this.skin.materialFront, this.textureScaleY,
					this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
			break;
		}
		writer.open(ValveElement.EDITOR_TAG)
				.put(ValveElement.COLOR, "0 215 172")
				.put(ValveElement.VISGROUPSHOWN, 1)
				.put(ValveElement.VISGROUPAUTOSHOWN, 1)
				.close();
		writer.close();
	}
}
