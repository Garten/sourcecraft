package main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import basic.Loggger;
import basic.Tuple;
import minecraft.Area;
import minecraft.ChunkPosition;
import minecraft.McaSection;
import minecraft.Minecraft;
import minecraft.Position;
import minecraft.WorldPiece;
import minecraft.map.BlockConverterContext;
import minecraft.map.DefaultSourceMap;
import minecraft.reader.RegionFile;
import minecraft.reader.nbt.McaReader;
import minecraft.reader.nbt.PlayerInLevelReader;
import minecraft.reader.nbt.PlayerReader;
import periphery.Place;
import source.SkinManager;

public class Converter {

	private ConvertTask converterData;

	private BlockConverterContext blockContent;

	public Converter(ConvertTask converterData) {
		this.converterData = converterData;
		DefaultSourceMap target = new DefaultSourceMap(converterData.getTexturePack(), converterData.getOption()
				.getScale());
		this.blockContent = new BlockConverterContext(converterData, target);
	}

	public Converter open(File fileFolder, ConvertTask converterData) throws IOException {
		Place place = converterData.getPlace();
		SkinManager.init(converterData.getTexturePack(), converterData.getOption()
				.getScale());
		Position start = place.getStart();
		Position end = place.getEnd();
		Position writeTarget = new Position(1, 1, 1);
		for (int mcX = start.getX(); mcX <= end.getX();) {
			Position diff = new Position();
			writeTarget.z = 1;
			for (int mcZ = start.getZ(); mcZ <= end.getZ();) {
				Loggger.log("from " + mcX + "," + mcZ + " to target " + writeTarget.x + " " + writeTarget.z);
				WorldPiece convertee = new WorldPiece(new Position(mcX, start.getY(), mcZ), end, writeTarget);
				this.readChunk(fileFolder, convertee, writeTarget);
				diff = convertee.getAreaXZ();
				writeTarget.z += diff.z;

				mcZ += diff.z;
			}
			writeTarget.x += diff.x;
			mcX += diff.x;
		}
		return this;
	}

	public void readChunk(File fileFolder, WorldPiece worldPiece, Position writeTarget) throws IOException {
		File file = Minecraft.getFileOfChunk(fileFolder, worldPiece);
		this.logReadingChunk(worldPiece, file);
		RegionFile regionfile = new RegionFile(file);
		if (file.exists() == false) {
			throw new IOException("File does not exist: " + file.toString());
		}
		DataInputStream inputStream = regionfile.getChunkDataInputStream(worldPiece.getLocalChunk()
				.getX(),
				worldPiece.getLocalChunk()
						.getZ());
		if (inputStream == null) {
			// throw new IOException("cannot find chunk in " + file.toString());
			Loggger.log("cannot find chunk file " + file.toString());
		} else {
			// Create NBTReader for chunk.
			McaReader reader = new McaReader(inputStream, this, worldPiece);
			reader.readChunk();
			inputStream.close();
		}
	}

	public void addMcaSection(McaSection section) {
		// TODO sections occur that with negative volume
		Tuple<Area, Position> toWrite = section.getBoundAndTarget();
		Position target = toWrite.getSecond();
		for (Position offset : toWrite.getFirst()) {
			Position writePos = Position.add(target, offset);
			this.blockContent.setMaterial(writePos, section.getBlock(offset));
			this.blockContent.setIsNextToAir(writePos, false);
		}
	}

	private void logReadingChunk(WorldPiece convertee, File file) {
		Loggger.log(convertee.toString());
		ChunkPosition chunk = convertee.getGlobalChunk();
		Loggger.log("reading global chunk (" + formatChunkNumber(chunk.getX()) + "," + formatChunkNumber(chunk.getZ())
				+ ") from " + file.toString());
	}

	private static String formatChunkNumber(int localChunk) {
		String string = localChunk + "";
		if (string.length() == 1) {
			string = " " + string;
		}
		return string;
	}

	public void convert(File file) {
		this.getMinecraftMap(this.converterData);
		Loggger.log("Saving to " + file);
		try {
			this.blockContent.write(file, this.converterData.getGame());
		} catch (IOException e) {
			Loggger.error(e.toString());
		}

	}

	public void getMinecraftMap(ConvertTask converterData) {
		File fileFolder = Minecraft.getRegionFolder(converterData.getPlace())
				.toFile();
		try {
			File mapFolder = Minecraft.getWorldFolder(converterData.getPlace());
			this.readLevelInformtaion(mapFolder);
			this.open(fileFolder, converterData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Loggger.error("Did not find any mca-files.");
		}
	}

	private Position readLevelInformtaion(File mapFolder) throws IOException {
		File playerFolder = new File(mapFolder, Minecraft.PLAYERS_FOLDER);
		File[] playerFiles = playerFolder.listFiles(file -> file.getName()
				.endsWith(Minecraft.DAT_ENDING));
		if (playerFiles != null && playerFiles.length > 0) { // TODO used for?
			File playerFile = playerFiles[0];
			if (playerFile != null) {
				Loggger.log("getting player info from " + playerFile.getName());
				DataInputStream stream = new DataInputStream(new GZIPInputStream(new FileInputStream(playerFile)));
				PlayerReader playerReader = new PlayerReader(stream);
				Position playerPosition = playerReader.read();
				return playerPosition;
			}
		}
		File levelFile = new File(mapFolder + File.separator + Minecraft.LEVEL_FILE_NAME);
		DataInputStream stream = new DataInputStream(new GZIPInputStream(new FileInputStream(levelFile)));

		PlayerInLevelReader levelReader = new PlayerInLevelReader(stream);
		Position localPlayerPosition = levelReader.read()
				.getPlayerPosition();
		return localPlayerPosition;
	}
}
