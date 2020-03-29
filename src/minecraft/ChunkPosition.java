package minecraft;

public class ChunkPosition {

	private int x;
	private int z;

	public ChunkPosition(Position position) {
		this(obtainChunkCoordinate(position.getX()), obtainChunkCoordinate(position.getZ()));
	}

	private static int obtainChunkCoordinate(int value) {
		if (value >= 0) {
			return value / Minecraft.CHUNK_SIZE_X;
		} else {
			return -((-value - 1) / Minecraft.CHUNK_SIZE_X) - 1;
			// treat value as a positive value
			// due to java rounds towards 0, and not down
		}
	}

	public ChunkPosition(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

	public Position getMaxPosition() {
		int resultX = ((this.x + 1) * Minecraft.CHUNK_SIZE_X) - 1;
		int resultZ = ((this.z + 1) * Minecraft.CHUNK_SIZE_Z) - 1;
		return new Position(resultX, Minecraft.MAX_Y, resultZ);
	}

	public ChunkPosition copy() {
		return new ChunkPosition(this.x, this.z);
	}

	@Override
	public String toString() {
		return "[" + this.x + " " + this.z + "]";
	}

	public String toFileName() {
		return "r." + this.x + "." + this.z + "." + Minecraft.ANVIL_ENDING;
	}
}
