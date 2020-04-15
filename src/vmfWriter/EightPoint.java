package vmfWriter;

import java.io.IOException;

import minecraft.Position;

public abstract class EightPoint extends Solid {

	private static final int LIGHTMAPSCALE = 16;

	protected Position a;
	protected Position b;
	protected Position c;
	protected Position d;
	protected Position e;
	protected Position f;
	protected Position g;
	protected Position h;

	protected double textureScaleX = 1;
	protected double textureScaleY = 1;
	protected double textureScaleZ = 1;

	public final void scale(int scale) {
		this.a.scale(scale);
		this.b.scale(scale);
		this.c.scale(scale);
		this.d.scale(scale);
		this.e.scale(scale);
		this.f.scale(scale);
		this.g.scale(scale);
		this.h.scale(scale);
	}

	public final Position[] getPointArray() {
		Position[] allPoints = new Position[8];
		allPoints[0] = this.a;
		allPoints[1] = this.b;
		allPoints[2] = this.c;
		allPoints[3] = this.d;
		allPoints[4] = this.e;
		allPoints[5] = this.f;
		allPoints[6] = this.g;
		allPoints[7] = this.h;
		return allPoints;
	}

	public final void move(int xDistance, int yDistance, int zDistance) {
		this.a.move(xDistance, yDistance, zDistance);
		this.b.move(xDistance, yDistance, zDistance);
		this.c.move(xDistance, yDistance, zDistance);
		this.d.move(xDistance, yDistance, zDistance);
		this.e.move(xDistance, yDistance, zDistance);
		this.f.move(xDistance, yDistance, zDistance);
		this.g.move(xDistance, yDistance, zDistance);
		this.h.move(xDistance, yDistance, zDistance);
	}

	private static final String SIDE_TAG = "side";
	private static final String PLANE_TAG = "plane";
	private static final String MATERIAL_TAG = "material";
	private static final String UAXIS_TAG = "uaxis";
	private static final String VAXIS_TAG = "vaxis";
	private static final String ROTATION_TAG = "rotation";
	private static final String LIGHTMAPSCALE_TAG = "lightmapscale";
	private static final String SMOOTHING_GROUPS_TAG = "smoothing_groups";

	protected static final Position TOP_U_AXIS = new Position(1, 0, 0);
	protected static final Position TOP_V_AXIS = new Position(0, -1, 0);
	protected static final Position BOTTOM_U_AXIS = new Position(-1, 0, 0);
	protected static final Position BOTTOM_V_AXIS = new Position(0, -1, 0);

	protected static final Position LEFT_U_AXIS = new Position(0, -1, 0);
	protected static final Position LEFT_V_AXIS = new Position(0, 0, -1);
	protected static final Position RIGHT_U_AXIS = new Position(0, 1, 0);
	protected static final Position RIGHT_V_AXIS = new Position(0, 0, -1);

	protected static final Position FRONT_U_AXIS = new Position(1, 0, 0);
	protected static final Position FRONT_V_AXIS = new Position(0, 0, -1);
	protected static final Position BACK_U_AXIS = new Position(-1, 0, 0);
	protected static final Position BACK_V_AXIS = new Position(0, 0, -1);

	public final void writeSide(ValveWriter writer, Position first, Position second, Position third, String skin, double textureScale1, double textureScale2,
			Position uAxis, Position vAxis) throws IOException {

		writer.open(SIDE_TAG)
				.put(ValveElement.ID_TAG, writer.getCounter()
						.getSideId())
				.put(PLANE_TAG, "(" + first.getString() + ") (" + second.getString() + ") (" + third.getString() + ")")
				.put(MATERIAL_TAG, skin)
				.put(UAXIS_TAG, "[" + uAxis.toAxisString() + " 0] " + this.skin.scale * textureScale1)
				.put(VAXIS_TAG, "[" + vAxis.toAxisString() + " 0] " + this.skin.scale * textureScale2)
				.put(ROTATION_TAG, 0)
				.put(LIGHTMAPSCALE_TAG, LIGHTMAPSCALE)
				.put(SMOOTHING_GROUPS_TAG, 0);
		writer.close();
	}
}
