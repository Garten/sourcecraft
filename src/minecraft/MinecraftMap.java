package minecraft;

import java.util.function.Predicate;

public interface MinecraftMap {

	public Block getBlock(Position position);

	public boolean isAirBlock(Position position);

	public void markAsConverted(Position position);

	public void markAsConverted(Position position, Position end);

	public boolean hasBlock(Position position, Predicate<Block> container);

	public int getScale();

	public Position getMovedPointInGridDimension(Position position, double x, double y, double z);

	public void setMaterial(Position position, Block material);
}
