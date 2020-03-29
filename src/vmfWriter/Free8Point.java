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
						.getBrushId());
		if (this.align) {
			this.writeSide(writer, this.b, this.f, this.g, this.skin.materialTop, this.textureScaleX, this.textureScaleY, horizontal1, horizontal2);
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, horizontal1, horizontal2);
			this.writeSide(writer, this.a, this.e, this.f, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, side1, side2);
			this.writeSide(writer, this.h, this.d, this.c, this.skin.materialRight, this.textureScaleX, this.textureScaleZ, side1, side2);
			this.writeSide(writer, this.e, this.h, this.g, this.skin.materialBack, this.textureScaleY, this.textureScaleZ, front1, front2);
			this.writeSide(writer, this.d, this.a, this.b, this.skin.materialFront, this.textureScaleY, this.textureScaleZ, front1, front2);
		} else {
			this.writeSide(writer, this.b, this.f, this.g, this.skin.materialTop, this.textureScaleX, this.textureScaleY, horizontal1, horizontal2);
			this.writeSide(writer, this.e, this.a, this.d, this.skin.materialBottom, this.textureScaleX, this.textureScaleY, horizontal1, horizontal2);
			this.writeSide(writer, this.a, this.e, this.f, this.skin.materialLeft, this.textureScaleX, this.textureScaleZ, front1, side2);
			this.writeSide(writer, this.h, this.d, this.c, this.skin.materialRight, this.textureScaleX, this.textureScaleZ, front1, side2);
			this.writeSide(writer, this.e, this.h, this.g, this.skin.materialBack, this.textureScaleY, this.textureScaleZ, side1, front2);
			this.writeSide(writer, this.d, this.a, this.b, this.skin.materialFront, this.textureScaleY, this.textureScaleZ, side1, front2);
		}
		writer.open(ValveElement.EDITOR_TAG)
				.put(ValveElement.COLOR, "0 215 172")
				.put(ValveElement.VISGROUPSHOWN, 1)
				.put(ValveElement.VISGROUPAUTOSHOWN, 1)
				.close();
		writer.close();
	}
}
