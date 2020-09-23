package periphery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import basic.Loggger;
import converter.actions.Action;
import converter.actions.actions.CenteredPointEntity;
import converter.actions.actions.CssLamp;
import converter.actions.actions.TallGrassTf2;
import vmfWriter.Color;

public class ConfigIO {

	private static final String ACTION_CLASS_LABEL = "action";

	public static void write(Config config, Path target) throws IOException {

		// adding all different container classes with their flag
		final RuntimeTypeAdapterFactory<Action> typeFactory = RuntimeTypeAdapterFactory
				.of(Action.class, ACTION_CLASS_LABEL)
				.registerSubtype(TallGrassTf2.class)
				.registerSubtype(CssLamp.class)
				.registerSubtype(CenteredPointEntity.class);

		// add the polymorphic specialization
		final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory)
				.setPrettyPrinting()
				.create();

//		GsonBuilder gsonBuilder = new GsonBuilder();
//		gsonBuilder.registerTypeAdapter(Action.class, new TypeAdapter<Action>() {
//
//			@Override
//			public Action read(JsonReader arg0) throws IOException {
//
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void write(JsonWriter arg0, Action arg1) throws IOException {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//		Gson gson = gsonBuilder.setPrettyPrinting()
//				.create();
		String toSafe = gson.toJson(config);

		Files.write(target, toSafe.getBytes());
	}

	public static Config obtain(Config config, Path path) {
		if (path.toFile()
				.exists()) {
			try {
				config = ConfigIO.read(config, path);
			} catch (IOException e1) {
				Loggger.log("No config at " + path + " found.");
			}
		} else {
			config = Config.getDetaulftConfig();
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

		gsonBuilder.registerTypeAdapter(periphery.ConvertOption.class,
				(InstanceCreator<ConvertOption>) arg0 -> new periphery.ConvertOption());
		gsonBuilder.registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Action.class, ACTION_CLASS_LABEL)
				.registerSubtype(TallGrassTf2.class)
				.registerSubtype(CssLamp.class)
				.registerSubtype(CenteredPointEntity.class));
		gsonBuilder.registerTypeAdapter(SourceGame.class, (InstanceCreator<SourceGame>) arg0 -> new SourceGame());
		return gsonBuilder.create();
	}
}
