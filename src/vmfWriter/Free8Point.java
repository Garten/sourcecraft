package vmfWriter;

import java.io.IOException;

import minecraft.Position;

public class Free8Point extends Cuboid {

	private boolean align;

	public Free8Point(Position[] p, Skin skin, boolean align) {
		super(p, skin);
		this.align = align;
	}

	@Override
	public void writeVmf(ValveWriter writer) throws IOException {
		writer.open(Solid.SOLID_TAG)
				.put(ValveElement.ID_TAG, writer.getCounter()
						.getNewBrushId());
		if (this.align) {
			this.writeSide(writer, this.aTop, this.eTop, this.hTop, this.skin.materialTop, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.a, this.e, this.eTop, this.skin.materialLeft, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.h, this.d, this.dTop, this.skin.materialRight, this.textureScaleX,
					this.textureScaleZ, LEFT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.e, this.h, this.hTop, this.skin.materialBack, this.textureScaleY,
					this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
			this.writeSide(writer, this.d, this.a, this.aTop, this.skin.materialFront, this.textureScaleY,
					this.textureScaleZ, FRONT_U_AXIS, FRONT_V_AXIS);
		} else {
			this.writeSide(writer, this.aTop, this.eTop, this.hTop, this.skin.materialTop, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX,
					this.textureScaleY, TOP_U_AXIS, TOP_V_AXIS);
			this.writeSide(writer, this.a, this.e, this.eTop, this.skin.materialLeft, this.textureScaleX,
					this.textureScaleZ, FRONT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.h, this.d, this.dTop, this.skin.materialRight, this.textureScaleX,
					this.textureScaleZ, FRONT_U_AXIS, LEFT_V_AXIS);
			this.writeSide(writer, this.e, this.h, this.hTop, this.skin.materialBack, this.textureScaleY,
					this.textureScaleZ, LEFT_U_AXIS, FRONT_V_AXIS);
			this.writeSide(writer, this.d, this.a, this.aTop, this.skin.materialFront, this.textureScaleY,
					this.textureScaleZ, LEFT_U_AXIS, FRONT_V_AXIS);
		}
		writer.open(ValveElement.EDITOR_TAG)
				.put(ValveElement.COLOR, "0 215 172")
				.put(ValveElement.VISGROUPSHOWN, 1)
				.put(ValveElement.VISGROUPAUTOSHOWN, 1)
				.close();
		writer.close();
	}
}
