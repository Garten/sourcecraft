package minecraft;

import java.io.IOException;

import basic.Loggger;
import basic.Tuple;
import minecraft.reader.nbt.BitNbtReader;
import minecraft.reader.nbt.NbtReader;
import source.Material;

public class McaSection {

	WorldPiece worldPiece;

	private int firstYPosition;

	private int[][][] block = new int[Minecraft.CHUNK_SIZE_X][Minecraft.SECTION_SIZE_Y][Minecraft.CHUNK_SIZE_Z];

	public McaSection(WorldPiece convertee) {
		this.worldPiece = convertee;
	}

	public WorldPiece getConvertee() {
		return this.worldPiece;
	}

	public int getBlock(Position position) {
		return this.block[position.x][position.y][position.z];
	}

	public int getFirstYPosition() {
		return this.firstYPosition;
	}

	public void readBlocksRaw(NbtReader source) throws IOException {
		int length = source.readLength();
		int unitSize = this.getUnitLength(length);
		BitNbtReader reader = new BitNbtReader(source, unitSize);
		for (int y = 0; y < Minecraft.SECTION_SIZE_Y; y++) {
			for (int z = 0; z < Minecraft.CHUNK_SIZE_Z; z++) {
				for (int x = 0; x < Minecraft.CHUNK_SIZE_X; x++) {
					this.block[x][y][z] = reader.readBits();
				}
			}
		}
	}

	private int getUnitLength(int length) {
		return length / 64;
	}

	public void translateRawInfo(McaBlock[] mapping) {
		if (mapping == null) {
			// some sections are empty and come without a mapping
			return;
		}
		for (int y = 0; y < Minecraft.SECTION_SIZE_Y; y++) {
			for (int z = 0; z < Minecraft.CHUNK_SIZE_Z; z++) {
				for (int x = 0; x < Minecraft.CHUNK_SIZE_X; x++) {
					int rawId = this.block[x][y][z];
					if (rawId >= 0 && rawId < mapping.length) {
						McaBlock block = mapping[rawId];
						this.block[x][y][z] = Material.get(block);
					} else {
						this.block[x][y][z] = Material._UNKOWN;
						Loggger.warn("unknown block encoding " + rawId + " at " + new Position(x, y, z).toString());
					}
				}
			}
		}
	}

	public void setHeight(int height) {
		this.firstYPosition = height * Minecraft.SECTION_SIZE_Y;
	}

	@Override
	public String toString() {
		return this.worldPiece.toString() + " @height " + this.firstYPosition;
	}

	public Tuple<Area, Position> getBoundAndTarget() {
		Position lower = this.getConvertee()
				.getLower();
		Position higher = this.getConvertee()
				.getHigher();
		int yMappedToStart = lower.getY();
		int absolutStartY = Math.max(this.getFirstYPosition(), lower.getY());
		int absoluteEndY = Math.min(this.getFirstYPosition() + (Minecraft.SECTION_SIZE_Y - 1), higher.getY());
		Position start = this.getConvertee()
				.getLower();
		start.setY(absolutStartY % Minecraft.SECTION_SIZE_Y);
		Position end = this.getConvertee()
				.getHigher();
		end.setY(absoluteEndY % Minecraft.SECTION_SIZE_Y);
		Position target = Position.subtract(this.getConvertee()
				.getVmfStart(), start);
		target.setY(absolutStartY - start.y - yMappedToStart + 1);
		if (absolutStartY > absoluteEndY) {
			start.y = 1;
			end.y = 0;
		}
		return new Tuple<>(new Area(start, end), target);
	}
}
