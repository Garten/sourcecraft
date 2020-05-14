package nbtReader;

import java.io.DataInputStream;
import java.io.IOException;

import minecraft.Position;

public class PlayerReader extends PlayerPositionReader {

	public PlayerReader(DataInputStream stream) {
		super(stream);
	}

	public Position read() throws IOException {
		int type = this.readTag();
		if (type == NbtTag.COMPOUND) {
			this.readTitle();
			return this.readPlayerData();
		}
		return null;
	}
}
