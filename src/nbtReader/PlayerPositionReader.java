package nbtReader;

import java.io.DataInputStream;
import java.io.IOException;

import minecraft.Position;

public abstract class PlayerPositionReader extends NbtReader {

	public PlayerPositionReader(DataInputStream stream) {
		super(stream);
	}

	private static final NamedTag POS = new NamedTag(NbtTag.LIST, "Pos");

	public Position readPlayerData() throws IOException {
		Position position = new Position();
		this.doCompound(NbtTasks.I.create()
				.put(POS, () -> this.readPosition(position)));
		return position;
	}

	private void readPosition(Position position) throws IOException {
		int listTag = this.readTag();
		if (listTag == NbtTag.DOUBLE && this.readLength() == 3) {
			double posX = this.readDouble();
			double posY = this.readDouble();
			double posZ = this.readDouble();
			position.setTo(new Position(posX, posY, posZ));
		} else {
			this.skipListOf(listTag);
		}
	}
}
