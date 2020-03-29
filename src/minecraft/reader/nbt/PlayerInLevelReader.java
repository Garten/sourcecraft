package minecraft.reader.nbt;

import java.io.DataInputStream;
import java.io.IOException;

import minecraft.Position;

public class PlayerInLevelReader extends PlayerPositionReader {

	private Position playerPosition = new Position();
	private Position worldSpawn = new Position();

	private final NbtTasks tasks;
	private static final NamedTag DATA = new NamedTag(NbtTag.COMPOUND, "Data");

	private static final NamedTag PLAYER = new NamedTag(NbtTag.COMPOUND, "Player");
	private static final NamedTag SPAWN_X = new NamedTag(NbtTag.INT, "SpawnX");
	private static final NamedTag SPAWN_Y = new NamedTag(NbtTag.INT, "SpawnY");
	private static final NamedTag SPAWN_Z = new NamedTag(NbtTag.INT, "SpawnZ");

	public PlayerInLevelReader(DataInputStream stream) {
		super(stream);

		this.tasks = NbtTasks.I.create()
				.put(DATA, () -> this.doCompound(NbtTasks.I.create()
						.put(PLAYER, () -> this.playerPosition = this.readPlayerData())
						.put(SPAWN_X, () -> this.worldSpawn.setX(this.readInt()))
						.put(SPAWN_Y, () -> this.worldSpawn.setY(this.readInt()))
						.put(SPAWN_Z, () -> this.worldSpawn.setZ(this.readInt()))));
	}

	public PlayerInLevelReader read() throws IOException {
		if (this.readTag() == NbtTag.COMPOUND) {
			this.readTitle();
			this.doCompound(this.tasks);
		}
		return this;
	}

	public Position getPlayerPosition() {
		return this.playerPosition;
	}

	public Position getWorldSpawn() {
		return this.worldSpawn;
	}
}
