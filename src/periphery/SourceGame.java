package periphery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import basic.Loggger;
import main.Steam;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class SourceGame {

	private String nameLong;
	private String nameShort;
	private String gamePath;
	private String defaultConvertOption;
	private RotateablePointEntity spawn; // =new NamedRotateablePointEntity().setName("info_player_start");
//	private static PointEntity SPAWN = new InfoPlayerStart(); // InfoPlayerTeamSpawn.setTeamNumber(0);

	private static final String MATERIALS_FOLDER = "materials";

	public static List<SourceGame> createDefaults() {
		ArrayList<SourceGame> result = new ArrayList<>();
		result.add(create().setLongName("Team Fortress 2")
				.setShortName("tf")
				.setDefaultConvertOption("defaultTF2")
				.setSpawnEntity(new RotateablePointEntity().setName("info_player_teamspawn")));
		result.add(create().setLongName("Counter-Strike Source")
				.setShortName("cstrike")
				.setDefaultConvertOption("defaultCss")
				.setSpawnEntity(new RotateablePointEntity().setName("info_player_teamspawn")));
		result.add(create().setLongName("Garrysmod")
				.setShortName("garrysmod")
				.setDefaultConvertOption("defaultGmod")
				.setSpawnEntity(new RotateablePointEntity().setName("info_player_start")));
		return result;
	}

	public static SourceGame create() {
		return new SourceGame();
	}

	public SourceGame() {

	}

	public SourceGame setSpawnEntity(RotateablePointEntity spawn) {
		this.spawn = spawn;
		return this;
	}

	public RotateablePointEntity getSpawnEntity() {
		return this.spawn;
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
		String path = Periphery.CONFIG.getSteamPath() + File.separator + Steam.STEAM_GAME_PATH() + File.separator
				+ Steam.STEAM_SDK_PATH + File.separator + this.getShortName() + File.separator
				+ Steam.STEAM_MAP_SRC_PATH;
		Loggger.log(path);
		return path;
	}

	public String getGameHammerPath(Config config) {
		String path = config.getSteamPath() + File.separator + Steam.STEAM_GAME_PATH() + File.separator + this.nameLong
				+ File.separator + "bin" + File.separator + "hammer.exe";
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
		if (Periphery.CONFIG.getSteamPath() == null) {
			return null;
		}
		return new File(String.join(File.separator, Periphery.CONFIG.getSteamPath()
				.toString(), Steam.STEAM_GAME_PATH(), this.getLongName(), this.getShortName(),
				SourceGame.MATERIALS_FOLDER, texturePack.getName()));
	}

	@Override
	public String toString() {
		return this.getLongName();
	}
}
