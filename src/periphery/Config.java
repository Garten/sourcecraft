package periphery;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import basic.Loggger;
import main.ConverterData;
import main.World;
import minecraft.Minecraft;
import source.addable.addable.Block;
import source.addable.addable.Cactus;
import source.addable.addable.CssLamp;
import source.addable.addable.EndPortalFrame;
import source.addable.addable.Fence;
import source.addable.addable.Fire;
import source.addable.addable.Liquid;
import source.addable.addable.Pane;
import source.addable.addable.PlayerSpawnCss;
import source.addable.addable.Slab;
import source.addable.addable.SnowBlock;
import source.addable.addable.TransparentBlock;
import source.addable.addable.stairs.StairsEast;
import source.addable.addable.stairs.StairsHighEast;
import source.addable.addable.stairs.StairsHighNorth;
import source.addable.addable.stairs.StairsHighSouth;
import source.addable.addable.stairs.StairsHighWest;
import source.addable.addable.stairs.StairsNorth;
import source.addable.addable.stairs.StairsSouth;
import source.addable.addable.stairs.StairsWest;
import source.addable.addable.tf2.LilypadTf2;
import source.addable.addable.tf2.PlayerSpawnTf2;
import source.addable.addable.tf2.SupplyTf2;
import source.addable.addable.tf2.TallGrassTf2;
import source.addable.addable.torch.Torch;
import source.addable.addable.torch.TorchEast;
import source.addable.addable.torch.TorchNorth;
import source.addable.addable.torch.TorchSouth;
import source.addable.addable.torch.TorchWest;
import source.addable.addable.vines.VinesEast;
import source.addable.addable.vines.VinesNorth;
import source.addable.addable.vines.VinesSouth;
import source.addable.addable.vines.VinesWest;
import vmfWriter.Color;

public class Config {

	// private String version;
	private List<SourceGame> games;
	private Queue<ConvertOption> options;
	private int windowPosX = 0;
	private int windowPosY = 0;
	private String minecraftPath;
	private String steamPath;
	private String steamUserName;

	private String place;
	private String game;
	private String convertOption;
	private String texturePack;
	private String savePath;

	public Config() {
		this.games = new LinkedList<>();
		this.options = new LinkedList<>();
	}

	public void weriteDefault() {
		this.games.addAll(SourceGame.createDefaults());
	}

	public String getSteamUserName() {
		return this.steamUserName;
	}

	public void setSteamUserName(String steamUserName) {
		this.steamUserName = steamUserName;
	}

	public void addConvertOption(ConvertOption option) {
		this.options.offer(option);
	}

	public ConvertOption[] getConvertOptions() {
		Object[] optionsArray = this.options.toArray();
		int length = optionsArray.length;
		ConvertOption[] result = new ConvertOption[length];

		for (int i = 0; i < length; i++) {
			result[i] = (ConvertOption) optionsArray[i];
		}
		return result;
	}

	public SourceGame getGame(String searchedName) {
		SourceGame result = null;
		Object[] gameArray = this.games.toArray();

		int length = gameArray.length;
		for (int i = 0; i < length; i++) {
			SourceGame test = (SourceGame) gameArray[i];
			String gameName = test.getLongName();
			if (gameName.equals(searchedName)) {
				result = test;
			}
		}
		return result;
	}

	public String[] getConvertOptionNames() {
		Object[] optionArray = this.options.toArray();
		int length = optionArray.length;
		String[] result = new String[length];

		for (int i = 0; i < length; i++) {
			result[i] = ((ConvertOption) optionArray[i]).getName();
		}
		return result;
	}

	public SourceGame[] getGamesArray() {
		Object[] gameArray = this.games.toArray();
		int length = gameArray.length;
		SourceGame[] result = new SourceGame[length];

		for (int i = 0; i < length; i++) {
			result[i] = (SourceGame) gameArray[i];
		}
		return result;
	}

	public List<SourceGame> getGames() {
		return this.games;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public void addGame(SourceGame game) {
		this.games.add(game);
	}

	public void setPack(String texturePack) {
		this.texturePack = texturePack;
	}

	public String getGameString() {
		return this.game;
	}

	public SourceGame getGame() {
		return this.getGame(this.game);
	}

	public String getTexturePack() {
		return this.texturePack;
	}

	public ConvertOption getDefaultConvertOption(String game) {
		return this.getDefaultConvertOption(this.getGame(game));
	}

	public ConvertOption getDefaultConvertOption(SourceGame game) {
		return this.getConvertOption(game.getDefaultConvertOption());
	}

	public ConvertOption getConvertOption(String searchedName) {
		Object[] optionsArray = this.options.toArray();
		int length = optionsArray.length;

		for (int i = 0; i < length; i++) {
			if (((ConvertOption) optionsArray[i]).getName()
					.equals(searchedName)) {
				return (ConvertOption) optionsArray[i];
			}
		}
		Loggger.log("Cannot find a convertOption named " + searchedName);
		return null;
	}

	public void setWindowPosX(String windowPosX) {
		if (!windowPosX.equals("")) {
			this.windowPosX = Integer.valueOf(windowPosX);
		}

	}

	public void setWindowPosY(String windowPosY) {
		if (!windowPosY.equals("")) {
			this.windowPosY = Integer.valueOf(windowPosY);
		}
	}

	public int getWindowPosX() {
		return this.windowPosX;
	}

	public int getWindowPosY() {
		return this.windowPosY;
	}

	public void setMinecraftPath(File path) {
		this.minecraftPath = path.toString();
	}

	public void setSteamPath(File path) {
		if (path == null) {
			return;
		}
		this.steamPath = path.toString();
	}

	public String getMinecraftPathString() {
		return this.minecraftPath;
	}

	public File getMinecraftPath() {
		return new File(this.minecraftPath);
	}

	public File getMinceraftSavePath() {
		return new File(this.minecraftPath + File.separator + Minecraft.SAVES_FOLDER);
	}

	public File getSteamPath() {
		if (this.steamPath == null) {
			return null;
		}
		return new File(this.steamPath);
	}

	public static boolean verifyMinecraftDirectory(File path) {
		File saves = new File(path + File.separator + Minecraft.SAVES_FOLDER);
		if (saves.exists() && saves.isDirectory()) {
			return true;
		}
		return false;
	}

	public File getWorldPath(World world) {
		return new File(this.getMinceraftSavePath() + File.separator + world.toString());
	}

	public void setConvertOption(String convertOption) {
		this.convertOption = convertOption;
	}

	public String getConvertOption() {
		return this.convertOption;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPlace() {
		return this.place;
	}

	public String getSavePath() {
		return this.savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getSafePath() {
		return this.savePath;
	}

	public void setConvertData(ConverterData converterData) {
		this.setPlace(converterData.getPlace()
				.getDisplayName());
		this.setGame(converterData.getGame()
				.getLongName());
		this.setConvertOption(converterData.getOption()
				.getName());
		this.setPack(converterData.getTexturePack()
				.getName());
	}

	public static Config getDetaulftConfig() {
		Config config = new Config();
		config.games = SourceGame.createDefaults();
//		config.games.add(SourceGame.create()
//				.setLongName("Team Fortress 2")
//				.setShortName("tf")
//				.setDefaultConvertOption("defaultTf2"));
//		config.games.add(SourceGame.create()
//				.setLongName("Counter-Strike Source")
//				.setShortName("cstrike")
//				.setDefaultConvertOption("defaultCss"));
//		config.games.add(SourceGame.create()
//				.setLongName("Garrysmod")
//				.setShortName("garrysmod")
//				.setDefaultConvertOption("defaultGmod"));
		config.options.add(ConvertOption.create()
				.setName("default")
				.setScale(40)
				.setSkyTexture("sky_day02_09")
				.setSunLight(new Color(255, 255, 200, 550))
				.setSunAmbient(new Color(200, 200, 200, 80))
				.setSunShadow(new Color(250, 250, 250, 0))
				.addAddable(PlayerSpawnCss.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(TorchNorth.class.getSimpleName())
				.addAddable(TorchSouth.class.getSimpleName())
				.addAddable(TorchWest.class.getSimpleName())
				.addAddable(TorchEast.class.getSimpleName())
				.addAddable(Torch.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(TransparentBlock.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(StairsWest.class.getSimpleName())
				.addAddable(StairsSouth.class.getSimpleName())
				.addAddable(StairsNorth.class.getSimpleName())
				.addAddable(StairsEast.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Slab.class.getSimpleName())
				.addAddable(Block.class.getSimpleName())
				.addAddable(StairsHighEast.class.getSimpleName())
				.addAddable(StairsHighWest.class.getSimpleName())
				.addAddable(StairsHighNorth.class.getSimpleName())
				.addAddable(StairsHighSouth.class.getSimpleName()));
		config.options.add(ConvertOption.create()
				.setName("defaultTf2")
				.setScale(48)
				.setSkyTexture("sky_day02_09")
				.setSunLight(new Color(255, 255, 200, 550))
				.setSunAmbient(new Color(200, 200, 200, 80))
				.setSunShadow(new Color(250, 250, 250, 0))
				.addAddable(LilypadTf2.class.getSimpleName())
				.addAddable(SupplyTf2.class.getSimpleName())
				.addAddable(PlayerSpawnTf2.class.getSimpleName())
				.addAddable(TorchEast.class.getSimpleName())
				.addAddable(TorchWest.class.getSimpleName())
				.addAddable(TorchSouth.class.getSimpleName())
				.addAddable(TorchNorth.class.getSimpleName())
				.addAddable(Block.class.getSimpleName())
				.addAddable(Slab.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(StairsEast.class.getSimpleName())
				.addAddable(StairsNorth.class.getSimpleName())
				.addAddable(StairsSouth.class.getSimpleName())
				.addAddable(StairsWest.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(TallGrassTf2.class.getSimpleName())
				.addAddable(TransparentBlock.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(StairsHighEast.class.getSimpleName())
				.addAddable(StairsHighWest.class.getSimpleName())
				.addAddable(StairsHighNorth.class.getSimpleName())
				.addAddable(StairsHighSouth.class.getSimpleName())
				.addAddable(Torch.class.getSimpleName()));
		config.options.add(ConvertOption.create()
				.setName("defaultCss")
				.setScale(48)
				.setSkyTexture("sky_day02_09")
				.setSunLight(new Color(255, 255, 200, 550))
				.setSunAmbient(new Color(200, 200, 200, 80))
				.setSunShadow(new Color(250, 250, 250, 0))
				.addAddable(StairsHighSouth.class.getSimpleName())
				.addAddable(StairsHighNorth.class.getSimpleName())
				.addAddable(StairsHighWest.class.getSimpleName())
				.addAddable(StairsHighEast.class.getSimpleName())
				.addAddable(Block.class.getSimpleName())
				.addAddable(Slab.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Fire.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(StairsEast.class.getSimpleName())
				.addAddable(StairsNorth.class.getSimpleName())
				.addAddable(StairsSouth.class.getSimpleName())
				.addAddable(StairsWest.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(TransparentBlock.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(PlayerSpawnCss.class.getSimpleName())
				.addAddable(CssLamp.class.getSimpleName())
				.addAddable(PlayerSpawnCss.class.getSimpleName()));
		config.options.add(ConvertOption.create()
				.setName("defaultGmod")
				.setScale(40)
				.setSkyTexture("sky_day02_09")
				.setSunLight(new Color(255, 255, 200, 550))
				.setSunAmbient(new Color(200, 200, 200, 80))
				.setSunShadow(new Color(250, 250, 250, 0))
				.addAddable(Block.class.getSimpleName())
				.addAddable(Slab.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(StairsEast.class.getSimpleName())
				.addAddable(StairsNorth.class.getSimpleName())
				.addAddable(StairsSouth.class.getSimpleName())
				.addAddable(StairsWest.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(TransparentBlock.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(StairsHighEast.class.getSimpleName())
				.addAddable(StairsHighWest.class.getSimpleName())
				.addAddable(StairsHighNorth.class.getSimpleName())
				.addAddable(StairsHighSouth.class.getSimpleName()));
		config.setPack("minecraft_original");
		return config;
	}

}
