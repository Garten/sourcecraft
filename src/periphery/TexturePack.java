package periphery;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import basic.Loggger;

public class TexturePack {

	private static final String TEXTURES_FOLDER = "textures";
	public static final String TEXTURE_OPTIONS_FILE = "options.json";
	private String name;
	private TextureOptions textureOptions;

	public TexturePack(String name) {
		this.name = name;
		this.textureOptions = new TextureOptions();
	}

	public static TextureOptions readTextureOptions(Path source) {
		try {
			Gson gson = new GsonBuilder().create();
			String fileAsString = new String(Files.readAllBytes(source));
			return gson.fromJson(fileAsString, TextureOptions.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TexturePack getTexturePack(String texturePackName) {
		TexturePack result = new TexturePack(texturePackName);
		Path textureOptionsPath = new File(String.join(File.separator, TEXTURES_FOLDER, texturePackName, TEXTURE_OPTIONS_FILE)).toPath();
		result.textureOptions = readTextureOptions(result.textureOptions, textureOptionsPath);
		return result;
	}

	public static TextureOptions readTextureOptions(TextureOptions textureOptions, Path source) {
		Gson gson = new GsonBuilder().create();
		String fileAsString;
		try {
			fileAsString = new String(Files.readAllBytes(source));
			return gson.fromJson(fileAsString, TextureOptions.class);
		} catch (IOException e) {
			Loggger.log("unable to read textureOptions " + e.getMessage());
			return textureOptions;
		}
	}

	public int getTextureSize() {
		if (this.textureOptions == null) {
			return -1;
		}
		return this.textureOptions.getScale();
	}

	public String getName() {
		return this.name;
	}

	public File getFolder() {
		return new File(TEXTURES_FOLDER + File.separator + this.getName());
	}

	public TextureOptions getTextureOptions() {
		return this.textureOptions;
	}

}
