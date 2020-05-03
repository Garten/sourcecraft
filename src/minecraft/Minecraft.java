package minecraft;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import basic.Loggger;
import gui.panel.LabeledCoordinates;
import main.Main;
import main.World;
import minecraft.reader.nbt.PlayerInLevelReader;
import periphery.Periphery;
import periphery.Place;

public class Minecraft {

	private static final String SAVES_FOLDER = "saves";
	private static final String REGION_FOLDER = "region";

	public static final int MAX_CHUNK_IN_FILE_X = 32; // number of last chunk
	public static final int MAX_CHUNK_IN_FILE_Z = 32;

	public static final int CHUNK_SIZE_X = 16;
	public static final int CHUNK_SIZE_Z = 16;

	public static final int SECTION_SIZE_Y = 16;
	public static final int MAX_Y = 256;

	public static final String ANVIL_ENDING = "mca";
	public static final String DAT_ENDING = ".dat";
	public static final String LEVEL_FILE_NAME = "level.dat";
	public static final String PLAYERS_FOLDER = "players";

	public static final int MAXIMUM_HEIGHT = 256;

	public static Path getMinecraftPath() {
		return Periphery.CONFIG.getMinceraftSavePath();
	}

	public static Path getCorrectMinecraftPath() {
		Path minecraftPath = Periphery.CONFIG.getMinceraftSavePath();
		if (minecraftPath == null) {
			return getNewMinecraftPath();
		}
		if (!(isMinecraftInputDirectory(minecraftPath))) {
			return getNewMinecraftPath();
		}
		Loggger.log("valid minecraft path: " + Periphery.CONFIG.getMinecraftPathString());
		return minecraftPath;
	}

	public static boolean isMinecraftInputDirectory(Path path) {
		return getPossibleWorlds(path).size() > 0;
	}

	private static Path getNewMinecraftPath() {
		Path minecraftPath;
		if (Main.isUnix()) {
			minecraftPath = Paths.get(System.getProperty("user.home"), ".minecraft");
		} else {
			minecraftPath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", ".minecraft",
					SAVES_FOLDER);
		}
		Periphery.CONFIG.setMinecraftPath(minecraftPath);
		Loggger.log("guessing minecraft path: " + Periphery.CONFIG.getMinecraftPathString());
		return minecraftPath;
	}

	public Vector<LabeledCoordinates> getPlayerCoordinates(World world) {
		File file = new File(Periphery.CONFIG.getWorldPath(world), "level.dat");
		Vector<LabeledCoordinates> vector = new Vector<>();
		try {
			DataInputStream stream = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
			PlayerInLevelReader reader = new PlayerInLevelReader(stream);
			Position position = reader.read()
					.getPlayerPosition();

			vector.add(new LabeledCoordinates("Player position", position, position));
			vector.add(new LabeledCoordinates("World origin", new Position(-20, 45, -20), new Position(20, 150, 20)));
			return vector;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vector;
	}

	public static File getFileOfChunk(File fileFolder, WorldPiece source) {
		int fileX = source.getFilePosition()
				.getX();
		int fileZ = source.getFilePosition()
				.getZ();
		return new File(fileFolder, "r." + fileX + "." + fileZ + "." + ANVIL_ENDING);
	}

	public static Vector<World> getPossibleWorlds() {
		return getPossibleWorlds(Periphery.CONFIG.getMinceraftSavePath());
	}

	public static Vector<World> getPossibleWorlds(Path savesPath) {
		Vector<World> result = new Vector<>();
		try {
			Files.list(savesPath)
					.filter(path -> {
						return Files.isDirectory(path.resolve(REGION_FOLDER));
					})
					.forEach(path -> result.add(new World(path.getParent(), path.getFileName()
							.toString())));
		} catch (IOException e) {
		}
		return result;
	}

//	public static Vector<World> getPossibleWorlds(Path savesPath) {
//		String[] list = file.list();
//		Vector<World> vector = new Vector<>();
//		if (list != null) {
//			for (String world : list) {
//				if (!(Files.isDirectory(Paths.get(Periphery.CONFIG.getMinceraftSavePath()
//						.toString(), world, REGION_FOLDER)))) {
//					continue;
//				}
//				try {
//					vector.addElement(new World(world));
//				} catch (IOException e) {
//					Loggger.log("cannot create world");
//				}
//
//			}
//		}
//		return vector;
//	}

	public static File getWorldFolder(Place place) {
		return new File(Periphery.CONFIG.getMinecraftPath() + File.separator + place.getWorld());
//		return new File(Periphery.CONFIG.getMinecraftPath(), "saves" + File.separator + place.getWorld());
////		return String.join(File.separator, Periphery.CONFIG.getMinecraftPathString(), place.getWorld());
	}

	public static Path getRegionFolder(Place place) {
		return Paths.get(getWorldFolder(place).toString(), REGION_FOLDER);
	}
}
