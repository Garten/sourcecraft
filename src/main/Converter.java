package main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import basic.Loggger;
import minecraft.Minecraft;
import minecraft.Position;
import minecraft.map.DefaultMinecraftMapConverter;
import minecraft.reader.nbt.PlayerInLevelReader;
import minecraft.reader.nbt.PlayerReader;
import periphery.Place;

public class Converter {

	public void convert(ConverterData converterData, File file) {
		this.convertMap(converterData, file);
		if (converterData.getUpdateTextures()) {

		}
	}

	private void convertMap(ConverterData converterData, File file) {
		DefaultMinecraftMapConverter map = this.getMinecraftMap(converterData);
		Loggger.log("Saving to " + file);
		try {
			map.write(file);
		} catch (IOException e) {
			Loggger.error(e.toString());
		}

	}

	public DefaultMinecraftMapConverter getMinecraftMap(ConverterData converterData) {
		File fileFolder = this.getFileFolderFromPlace(converterData.getPlace());
		try {
			File mapFolder = Minecraft.getSavesFolder(converterData.getPlace());
			this.readLevelInformtaion(mapFolder);
			return DefaultMinecraftMapConverter.open(fileFolder, converterData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Loggger.error("Did not find any mca-files.");
		return null;
	}

	private Position readLevelInformtaion(File mapFolder) throws IOException {
		File playerFolder = new File(mapFolder, Minecraft.PLAYERS_FOLDER);
		File[] playerFiles = playerFolder.listFiles(file -> file.getName()
				.endsWith(Minecraft.DAT_ENDING));
		if (playerFiles != null && playerFiles.length > 0) {
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

	public File getFileFolderFromPlace(Place place) {
		File result = new File(Minecraft.getRegionFolder(place));
		return result;
	}
}
