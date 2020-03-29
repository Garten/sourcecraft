package periphery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import basic.Loggger;
import main.Steam;

public class SourceGame {

	private String nameLong;
	private String nameShort;
	private String gamePath;
	private String defaultConvertOption;

	private static final String MATERIALS = "materials";

	public static List<SourceGame> createDefaults() {
		ArrayList<SourceGame> result = new ArrayList<>();
		result.add(create("Team Fortress 2").setShortName("tf")
				.setDefaultConvertOption("defaultTF2"));
		result.add(create("Counter-Strike Source").setShortName("cstrike")
				.setDefaultConvertOption("defaultCss"));
		result.add(create("Garrysmod").setShortName("garrysmod")
				.setDefaultConvertOption("defaultGmod"));
		return result;
	}

	public static SourceGame create() {
		return new SourceGame();
	}

	public SourceGame() {

	}

	public SourceGame(String nameLong) {
		this.nameLong = nameLong;
	}

	public static SourceGame create(String nameLong) {
		return new SourceGame(nameLong);
	}

	public void setGameTargetSavePath(File filePath) {
		this.gamePath = filePath.getParent();
	}

	public String getGamePath() {
		if (this.gamePath != null) {
			File f = new File(this.gamePath);
			if (f.exists()) {
				return this.gamePath;
			}
		}
		String path = Periphery.CONFIG.getSteamPath() + File.separator + Steam.STEAM_GAME_PATH() + File.separator + Steam.STEAM_SDK_PATH + File.separator
				+ this.getShortName() + File.separator + Steam.STEAM_MAP_SRC_PATH;
		Loggger.log(path);
		return path;
	}

	public String getGameHammerPath(Config config) { // TODO
		String path = config.getSteamPath() + File.separator + Steam.STEAM_GAME_PATH() + File.separator + this.nameLong + File.separator + "bin"
				+ File.separator + "hammer.exe";
		Loggger.log(path);
		return path;
	}

	public SourceGame setLongName(String nameLong) {
		this.nameLong = nameLong;
		return this;
	}

	public String getLongName() {
		return this.nameLong;
	}

	public SourceGame setShortName(String nameShort) {
		this.nameShort = nameShort;
		return this;
	}

	public String getShortName() {
		return this.nameShort;
	}

	public SourceGame setDefaultConvertOption(String name) {
		this.defaultConvertOption = name;
		return this;
	}

	public String getDefaultConvertOption() {
		return this.defaultConvertOption;
	}

	public File getMatriealPath(TexturePack texturePack) {
		return new File(String.join(File.separator, Periphery.CONFIG.getSteamPath()
				.toString(), Steam.STEAM_GAME_PATH(), this.getLongName(), this.getShortName(), SourceGame.MATERIALS, texturePack.getName()));
	}

	@Override
	public String toString() {
		return this.getLongName();
	}
}
