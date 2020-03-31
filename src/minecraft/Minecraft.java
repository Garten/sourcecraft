package minecraft;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import basic.Loggger;
import gui.panel.LabeledCoordinates;
import main.Main;
import main.World;
import minecraft.reader.nbt.PlayerInLevelReader;
import periphery.Config;
import periphery.Periphery;
import periphery.Place;

public class Minecraft {

	public static final String SAVES_FOLDER = "saves";

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

	public static File getMinecraftPath() {
		String minecraftPathString = Periphery.CONFIG.getMinecraftPathString();
		if (minecraftPathString == null) {
			return getNewMinecraftPath();
		}
		File minecraftPath = new File(minecraftPathString);
		if (!minecraftPath.exists() || !(Config.verifyMinecraftDirectory(minecraftPath))) {
			return getNewMinecraftPath();
		}
		Loggger.log("valid minecraft path: " + Periphery.CONFIG.getMinecraftPathString());
		return minecraftPath;
	}

	private static File getNewMinecraftPath() {
		File minecraftPath;
		if (Main.isUnix()) {
			minecraftPath = new File(String.join(File.separator, System.getProperty("user.home"), ".minecraft"));
		} else {
			minecraftPath = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\");
		}
		Periphery.CONFIG.setMinecraftPath(minecraftPath);
		Loggger.log("guessing minecraft path: " + Periphery.CONFIG.getMinecraftPathString());
		return minecraftPath;
	}

	public static final int MAXIMUM_HEIGHT = 256;

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

	public static Vector<World> getPossibleWorlds() {
		File file = Periphery.CONFIG.getMinceraftSavePath();
		String[] list = file.list();
		Vector<World> vector = new Vector<>();
		if (list != null) {
			for (String world : list) {
				try {
					vector.addElement(new World(world));
				} catch (IOException e) {
					Loggger.log("cannot create world");
				}

			}
		}
		return vector;
	}

	public static File getSavesFolder(Place place) {
		return new File(Periphery.CONFIG.getMinecraftPath(), "saves" + File.separator + place.getWorld());
//		return String.join(File.separator, Periphery.CONFIG.getMinecraftPathString(), place.getWorld());
	}

	public static String getRegionFolder(Place place) {
		return getSavesFolder(place) + File.separator + "region";
	}
}
