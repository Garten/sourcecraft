package periphery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import basic.Loggger;
import vmfWriter.Color;

public class ConfigIO {

	public static void write(Config config, Path target) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.create();
		String toSafe = gson.toJson(config);

		Files.write(target, toSafe.getBytes());
	}

	public static Config obtain(Config config, Path path) {
		try {
			if (path.toFile()
					.exists()) {
				config = ConfigIO.read(config, path);
			} else {
				config = Config.getDetaulftConfig();
			}
		} catch (IOException e1) {
			Loggger.log("No fonig at " + path + " found.");
		}
		return config;
	}

	public static Config read(Config config, Path source) throws IOException {
		Gson gson = getGson();
		String fileAsString = new String(Files.readAllBytes(source));
		config = gson.fromJson(fileAsString, Config.class);

		return config;
	}

	private static Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(periphery.Place.class, (InstanceCreator<Place>) arg0 -> new periphery.Place());

		gsonBuilder.registerTypeAdapter(vmfWriter.Color.class, (InstanceCreator<Color>) arg0 -> new vmfWriter.Color());

		gsonBuilder.registerTypeAdapter(periphery.ConvertOption.class, (InstanceCreator<ConvertOption>) arg0 -> new periphery.ConvertOption());

		gsonBuilder.registerTypeAdapter(SourceGame.class, (InstanceCreator<SourceGame>) arg0 -> new SourceGame());
		return gsonBuilder.create();
	}
}
