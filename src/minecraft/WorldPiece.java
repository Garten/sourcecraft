package minecraft;

public class WorldPiece {

	private Position vmfStart;

	private ChunkPosition globalChunkPosition;

	private ChunkPosition localChunkPosition;

	private ChunkPosition filePosition;

	private Position lower;
	private Position higher;

	public WorldPiece(Position position, Position end, Position vmfStart) {
		this.vmfStart = vmfStart.copy();
		this.globalChunkPosition = new ChunkPosition(position);

		int lowerX = getLowerLocalCoordinate(position.getX());
		int lowerZ = getLowerLocalCoordinate(position.getZ());
		this.lower = new Position(lowerX, position.y, lowerZ);
		Position chunkStart = new Position(position.x - lowerX, position.y, position.z - lowerZ);

		this.higher = Position.subtract(end, chunkStart);
		this.higher.y = end.y;
		this.higher = Position.capBelow(this.higher, new Position(Minecraft.CHUNK_SIZE_X, Minecraft.MAX_Y, Minecraft.CHUNK_SIZE_Z));

		// local chunk position
		int x = getLocalCunkCoordinate(this.globalChunkPosition.getX());
		int z = getLocalCunkCoordinate(this.globalChunkPosition.getZ());
		this.localChunkPosition = new ChunkPosition(x, z);

		this.filePosition = this.getFileOfChunk(this.globalChunkPosition);

		assert this.higher != null;
		assert this.lower != null;
	}

	private static final int getLowerLocalCoordinate(int value) {
		if (value >= 0) {
			return value % Minecraft.CHUNK_SIZE_X;
		} else {
			// 'negative' chunk positions range from -16 to -1 instead of 0 to 15.
			return ((value + 1) % Minecraft.CHUNK_SIZE_X) + Minecraft.CHUNK_SIZE_X - 1;
		}
	}

	private ChunkPosition getFileOfChunk(ChunkPosition global) {
		int globalX = global.getX();
		int fileX, fileZ;
		if (globalX >= 0) {
			fileX = globalX / Minecraft.MAX_CHUNK_IN_FILE_X;
		} else {
			fileX = ((globalX + 1) / Minecraft.MAX_CHUNK_IN_FILE_X) - 1;
		}
		int globalZ = global.getZ();
		if (globalZ >= 0) {
			fileZ = globalZ / Minecraft.MAX_CHUNK_IN_FILE_Z;
		} else {
			fileZ = ((globalZ + 1) / Minecraft.MAX_CHUNK_IN_FILE_Z) - 1;
		}
		return new ChunkPosition(fileX, fileZ);
	}

	public ChunkPosition getFilePosition() {
		return this.filePosition.copy();
	}

	public Position getLower() {
		return this.lower.copy();
	}

	public Position getHigher() {
		return this.higher.copy();
	}

	public ChunkPosition getGlobalChunk() {
		return this.globalChunkPosition;
	}

	public ChunkPosition getLocalChunk() {
		return this.localChunkPosition;
	}

	private static final int getLocalCunkCoordinate(int value) {
		if (value >= 0) {
			return value % Minecraft.MAX_CHUNK_IN_FILE_X;
		} else {
			// 'negative' chunk positions range from -16 to -1 instead of 0 to 15.
			return ((value + 1) % Minecraft.MAX_CHUNK_IN_FILE_X) + Minecraft.MAX_CHUNK_IN_FILE_X - 1;
		}
	}

	public Bounds createBounds() {
		return new Bounds(this.getLower(), this.getHigher());
	}

	public Position getAreaXZ() {
		Position result = Position.areaSpan(this.lower, this.higher);
		result.y = 0;
		return result;
	}

	@Override
	public String toString() {
		return this.filePosition.toFileName() + " local " + this.localChunkPosition.toString() + " bounds " + this.lower.toBracketString() + " to "
				+ this.higher.toBracketString() + " -> " + this.vmfStart.toBracketString();
	}

	public Position getVmfStart() {
		return this.vmfStart.copy();
	}

}
