package periphery;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import basic.Loggger;

public class Periphery {

	private static final Path CONFIG_PATH = new File("config.json").toPath();
	private static final Path PLACES_PATH = new File("places.json").toPath();

	private static final String SC_TEXTURE_FOLDER = "textures";

	public static Config CONFIG = new Config();
	public static Places PLACES = new Places();

	public static void init() {
		CONFIG = new Config();
		PLACES = new Places();
		obtainConfig();
		obtainPlaces();
	}

	public static void obtainConfig() {
		CONFIG = ConfigIO.obtain(CONFIG, CONFIG_PATH);
	}

	public static void writeConfig() {
		try {
			ConfigIO.write(CONFIG, CONFIG_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writePlaces() {
		writePlaces(PLACES);
	}

	public static void obtainPlaces() {
		PLACES = new Places();
		try {
			Gson gson = new GsonBuilder().create();
			if (PLACES_PATH.toFile()
					.exists()) {
				String fileAsString = new String(Files.readAllBytes(PLACES_PATH));
				PLACES = gson.fromJson(fileAsString, Places.class);
			}
		} catch (IOException e) {
			Loggger.log(e.getMessage());
		}
	}

	private static void writePlaces(Places places) {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.create();
		String toSafe = gson.toJson(places);
		try {
			Files.write(PLACES_PATH, toSafe.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void write() {
		Periphery.writeConfig();
		Periphery.writePlaces();
	}

	public static String[] detectTexturePacks() {
		File file = new File(SC_TEXTURE_FOLDER + File.separator);
		File[] files = file.listFiles(new DirectoryFilter());
		if (files == null) {
			// TODO
			Loggger.log("no textures to install found");
		} else {
			int length = files.length;
			String[] detectedTexturePacks = new String[length];
			int i = 0;
			for (File f : files) {
				detectedTexturePacks[i] = f.getName();
				i++;
			}
			return detectedTexturePacks;
		}
		return null;
	}
}
