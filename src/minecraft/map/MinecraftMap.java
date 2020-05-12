package minecraft.map;

import java.util.function.Predicate;

import minecraft.Block;
import minecraft.Position;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;

public interface MinecraftMap {

	public Block getMaterial(Position position);

	public boolean hasMaterial(Position position, Predicate<Block> container);

	public boolean isAirBlock(Position position);

	public boolean isAir(Position position);

	public boolean isNextToAir(Position position);

	public void markAsConverted(Position position);

	public void markAsConverted(Position position, Position end);

	public boolean hasOrHadMaterial(Position position, Predicate<Block> container);

	public int getScale();

	public Position getMovedPointInGridDimension(Position position, double x, double y, double z);

	public void setMaterial(Position position, Block material);

	public Cuboid createCuboid(Position start, Position end, Block material);

	public Cuboid createCuboid(Position start, Position end, int positionarts, Position offset, Position negativeOffset,
			Block material);

	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align,
			Block material);
}
