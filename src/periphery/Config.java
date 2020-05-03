package periphery;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import basic.Loggger;
import main.ConverterData;
import main.World;
import source.addable.addable.Block;
import source.addable.addable.Cactus;
import source.addable.addable.CssLamp;
import source.addable.addable.DetailBlock;
import source.addable.addable.EndPortalFrame;
import source.addable.addable.Fence;
import source.addable.addable.Fire;
import source.addable.addable.Liquid;
import source.addable.addable.Pane;
import source.addable.addable.PlayerSpawnCss;
import source.addable.addable.SnowBlock;
import source.addable.addable.stairs.SlabBottom;
import source.addable.addable.stairs.SlabTop;
import source.addable.addable.tf2.LilypadTf2;
import source.addable.addable.tf2.PlayerSpawnTf2;
import source.addable.addable.tf2.SupplyTf2;
import source.addable.addable.tf2.TallGrassTf2;
import source.addable.addable.torch.Torch;
import source.addable.addable.torch.TorchEast;
import source.addable.addable.torch.TorchNorth;
import source.addable.addable.torch.TorchSouth;
import source.addable.addable.torch.TorchWest;
import source.addable.addable.ttt.TttEntites;
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

	public void setMinecraftPath(Path path) {
		this.minecraftPath = path.toString();
	}

	public void setSteamPath(Path path) {
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

	public Path getMinceraftSavePath() {
		if (this.minecraftPath == null) {
			return null;
		}
		return Paths.get(this.minecraftPath);
	}

	public Path getSteamPath() {
		if (this.steamPath == null) {
			return null;
		}
		return Paths.get(this.steamPath);
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
				.addAddable(DetailBlock.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(SlabBottom.class.getSimpleName())
				.addAddable(Block.class.getSimpleName())
				.addAddable(SlabTop.class.getSimpleName()));
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
				.addAddable(SlabBottom.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(TallGrassTf2.class.getSimpleName())
				.addAddable(DetailBlock.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(SlabTop.class.getSimpleName())
				.addAddable(Torch.class.getSimpleName()));
		config.options.add(ConvertOption.create()
				.setName("defaultCss")
				.setScale(48)
				.setSkyTexture("sky_day02_09")
				.setSunLight(new Color(255, 255, 200, 550))
				.setSunAmbient(new Color(200, 200, 200, 80))
				.setSunShadow(new Color(250, 250, 250, 0))
				.addAddable(Block.class.getSimpleName())
				.addAddable(SlabBottom.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Fire.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(DetailBlock.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(SlabTop.class.getSimpleName())
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
				.addAddable(SlabBottom.class.getSimpleName())
				.addAddable(Cactus.class.getSimpleName())
				.addAddable(Fence.class.getSimpleName())
				.addAddable(Pane.class.getSimpleName())
				.addAddable(Liquid.class.getSimpleName())
				.addAddable(DetailBlock.class.getSimpleName())
				.addAddable(SnowBlock.class.getSimpleName())
				.addAddable(VinesSouth.class.getSimpleName())
				.addAddable(VinesNorth.class.getSimpleName())
				.addAddable(VinesWest.class.getSimpleName())
				.addAddable(VinesEast.class.getSimpleName())
				.addAddable(EndPortalFrame.class.getSimpleName())
				.addAddable(SlabTop.class.getSimpleName())
				.addAddable(TorchNorth.class.getSimpleName())
				.addAddable(TorchSouth.class.getSimpleName())
				.addAddable(TorchWest.class.getSimpleName())
				.addAddable(TorchEast.class.getSimpleName())
				.addAddable(Torch.class.getSimpleName())
				.addAddable(TttEntites.class.getSimpleName()));
		config.setPack("minecraft_original");
		config.setGame("Garrysmod");
		return config;
	}

}
