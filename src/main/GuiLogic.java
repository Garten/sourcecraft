package main;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;

import basic.Loggger;
import basic.RunnableWith;
import gui.Gui;
import gui.panel.LabeledCoordinates;
import minecraft.Minecraft;
import minecraft.Position;
import periphery.Config;
import periphery.Periphery;
import periphery.Place;
import periphery.SourceGame;
import periphery.TexturePack;

public class GuiLogic implements RunnableWith<Gui> {

	private static final String VMF_ENDING = ".vmf";
	private Gui gui;

	private final static Color COLOR_DIRECTORY_FOUND = new Color(175, 255, 175);
	private final static Color COLOR_DIRECTORY_MISSING = Color.PINK;

	private boolean minecraftPathOk = false;
	private boolean sourcePathOk = false;

	@Override
	public void run(Gui gui) {
		this.gui = gui;
		this.initSetup();
		this.initInput();
		this.initOutput();
		this.initDetails();
		this.initResult();
	}

	private void initResult() {
		this.gui.setUponShowFile(() -> {
			String outputName = this.gui.getOutputFile();
			try {
				if (Desktop.getDesktop()
						.isSupported(Desktop.Action.OPEN)) {
					Desktop.getDesktop()
							.open(new File(outputName));
				}
			} catch (IOException e) {
				Loggger.log(e.getMessage());
			}
		});

		this.gui.setOpenHammerEditor(() -> {
			try {
				String outputName = Steam.getHammerPath(this.gui.getSourceGame());
				Loggger.log("executing: " + outputName);
				String[] cmd = { outputName };
				Runtime.getRuntime()
						.exec(cmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void initDetails() {
		String[] convertOptionNames = Periphery.CONFIG.getConvertOptionNames();
		this.gui.setPossibleConverterOptions(convertOptionNames);

		String[] textureNames = Periphery.detectTexturePacks();
		this.gui.setPossibleTextures(textureNames);
		final String texturePack = Periphery.CONFIG.getTexturePack();
		this.gui.setSelectedTexturePack(texturePack);
		this.informAboutTexturePack(texturePack);
		this.gui.setUponSelectTexturePack(pack -> {
			this.informAboutTexturePack(pack);
		});
	}

	private void initOutput() {
		this.gui.setPossibleGames(Periphery.CONFIG.getGames());

		this.gui.setUponSelectedGame(gameName -> {
			SourceGame game = Periphery.CONFIG.getGame(gameName);
			this.handleGameSelect(game);
		});
		SourceGame game = Periphery.CONFIG.getGame();

		this.handleGameSelect(game);

		this.gui.setButtonSelectOutputFile(path -> {
			Loggger.log("searching new output for " + path);
			File newFile = this.changeFile(new File(path));
			if (newFile.isDirectory()) {
				newFile = new File(newFile, "test.vmf");
			}
			Loggger.log(newFile.toString());
			this.gui.setOutputFileName(newFile.toString());
			Periphery.CONFIG.setSavePath(newFile.toString());
			Periphery.write();
		});

		this.gui.setUponEditOutput(this::testOutputFile);
		this.testOutputFile();
	}

	private void initInput() {

		// world
		Vector<World> worldNames = Minecraft.getPossibleWorlds();
		this.gui.setPossibleWorlds(worldNames);

		// place
		Vector<Place> placeNames = Periphery.PLACES.getPlaces();
		this.gui.setPossiblePlaces(placeNames);

		this.gui.setValidatePlace(place -> {
			if (place == null) {
				return false;
			}
			if (place.correctCoordinates()) {
				this.gui.setPlace(place);
				return false;
			}
			return true;
		});

		// place load
		this.gui.setLoadPlace(place -> {
			this.gui.setPlace(place);
		});
		Place currentPlace = this.gui.getPlace();
		this.gui.setPlace(currentPlace);

		this.gui.setDeletePlace(place -> {
			Periphery.PLACES.deletePlace(place);
			this.gui.setPossiblePlaces(Periphery.PLACES.getPlaces());
		}); // TODO

		this.gui.setLoadCoordinates(labeledCoordinates -> {
			this.gui.setLabeledCoordinates(labeledCoordinates);
			this.gui.setPlace(Place.create()
					.setStart(labeledCoordinates.getStart())
					.setEnd(labeledCoordinates.getEnd())
					.setWorld(this.gui.getWorld()
							.getName()));
		});

		// set player coordinates when world is selected
		this.gui.setUponWorldSelected(world -> {
			if (world == null) {
				return;
			}
			LabeledCoordinates playerPosition = world.getPlayerPosition();
			Vector<LabeledCoordinates> labeledCoordinates = new Vector<>();

			labeledCoordinates.add(playerPosition);
			labeledCoordinates.add(world.getWorldSpawnPosition());
			labeledCoordinates.add(new LabeledCoordinates("World origin", new Position(-20, 45, -20), new Position(20, 150, 20)));
			this.gui.setPossibleCoordinates(labeledCoordinates);
			this.gui.setLabeledCoordinates(new LabeledCoordinates(World.PLAYER_POSITION));
		});
		this.gui.setWorld(Place.getWorld(currentPlace));
	}

	private void initSetup() {
		this.gui.setMinecraftPath(Minecraft.getMinecraftPath());
		this.updatedMinecraftPath();
		this.gui.setEditedTextFieldMinecraftPath(this::updatedMinecraftPath);

		// steam path
		this.gui.setSourcePath(Steam.getSteamPath());
		this.updateDisplayForSteam();
		this.gui.setEditedTextFieldSourcePath(this::updateDisplayForSteam);

		this.gui.setSelectMinecraftPath(path -> {
			File newPath = this.changePath(new File(path));
			this.gui.setMinecraftPath(newPath);
			Periphery.CONFIG.setMinecraftPath(newPath);
			Periphery.write();
		});

		this.gui.setSelectSourcePath(path -> {
			File newPath = this.changePath(new File(path));
			this.gui.setSourcePath(newPath);
			Periphery.CONFIG.setSteamPath(newPath);
			Periphery.write();
		});
	}

	private void updatedMinecraftPath() {
		String minecraftPathString = this.gui.getMincraftPath();
		if (Config.verifyMinecraftDirectory(new File(minecraftPathString))) {
			this.gui.setTextFieldMincraftPathColor(COLOR_DIRECTORY_FOUND);
			this.minecraftPathOk = true;
		} else {
			this.gui.setTextFieldMincraftPathColor(COLOR_DIRECTORY_MISSING);
			this.minecraftPathOk = false;
		}
		this.updateContinueButton();
	}

	private void updateDisplayForSteam() {
		File steamPath = this.gui.getSourcePath();
		if (Steam.isSteamPath(steamPath)) {
			this.gui.setTextFieldSteamPathColor(COLOR_DIRECTORY_FOUND);
//			gui.setLabelIssourcesdkinstalled("Source SDK is installed."); // TODO
//			gui.setVisibilityLabelIssourcesdkinstalled(true);
			this.sourcePathOk = true;
		} else {
			this.gui.setTextFieldSteamPathColor(COLOR_DIRECTORY_MISSING);
//			gui.setLabelIssourcesdkinstalled("Source SDK is Not installed.");
//			gui.setVisibilityLabelIssourcesdkinstalled(true);
			this.sourcePathOk = false;
		}
		this.updateContinueButton();
	}

	private void updateContinueButton() {
		this.gui.setEnabledButtonContinueAtSetup(this.minecraftPathOk && this.sourcePathOk);
	}

	private void informAboutTexturePack(String texturePackName) {
		Loggger.log(texturePackName);
		TexturePack texturePack = TexturePack.getTexturePack(texturePackName);
		boolean upToDate = Steam.areTexturesUpToDate(this.gui.getSourceGame(), texturePack);
		this.gui.setVisibleTextTexturesUpToDate(upToDate);
	}

	private void handleGameSelect(SourceGame game) {
		String fileName = Steam.getGamePathString(game);
		if (fileName != null) {
			fileName += File.separator + "test" + VMF_ENDING;
			Loggger.log("changing output to " + fileName);
			this.gui.setOutputFileName(fileName);
		}
	}

	private void testOutputFile() {
		String outputName = this.gui.getOutputFile();
		File output = new File(outputName);
		this.gui.setOutputExistsVisibel(output.exists());
	}

	public File changePath(File path) {
		return this.changePath(path, JFileChooser.DIRECTORIES_ONLY);
	}

	public File changeFile(File file) {
		return this.changePath(file, JFileChooser.FILES_AND_DIRECTORIES);
	}

	private File changePath(File field, int selectionFlag) {
		File current = field;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(selectionFlag);
		if (current.exists()) {
			fileChooser.setCurrentDirectory(current);
		} else {
			String fileParent = current.getParent();
			if (fileParent != null) {
				current = new File(current.getParent());
				fileChooser.setCurrentDirectory(current);
			}
		}
		int state = fileChooser.showOpenDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			if (fileChooser.getSelectedFile() != null) {
				current = fileChooser.getSelectedFile();
			}
		}
		return current;
	}
}
