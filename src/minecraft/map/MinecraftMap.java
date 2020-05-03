package minecraft.map;

import minecraft.Position;
import source.MaterialFilter;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;
import vmfWriter.Orientation;
import vmfWriter.Ramp;

public interface MinecraftMap {

	public int getMaterial(Position position);

	public boolean hasMaterial(Position position, int... materials);

	public boolean isAirBlock(Position position);

	public boolean isAirBlockInitiate(Position position);

	public boolean isNextToAir(Position position);

	public void markAsConverted(Position position);

	public void markAsConverted(Position position, Position end);

	public boolean hasOrHadMaterial(Position position, int... materials);

	public boolean hasOrHadMaterial(Position position, MaterialFilter filter);

	public int getScale();

	public Position getMovedPointInGridDimension(Position position, double x, double y, double z);

	public void setMaterial(Position position, int material);

	public Cuboid createCuboid(Position start, Position end, int material);

	public Cuboid createCuboid(Position start, Position end, int positionarts, Position offset, Position negativeOffset,
			int material);

	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align,
			int material);

	public Ramp createRamp(Cuboid cuboid, Orientation orientation);

	public Ramp createRampCuttet(Cuboid cuboid, Orientation orientation, Orientation cut1, Orientation cut2);
}
