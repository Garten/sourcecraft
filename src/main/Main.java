package main;

import java.io.File;

import javax.swing.JOptionPane;

import basic.Loggger;
import gui.Gui;
import minecraft.TextureFolderMover;
import periphery.ConvertOption;
import periphery.Periphery;
import periphery.Place;
import periphery.TexturePack;
import source.Material;

public class Main {

	public static final String TITLE = "Sourcecraft - Minecraft to Source converter";
	public static final String VERSION = "3.0";
	public static final String AUTHOR = "garten";
	public static final String LICENSE = "GNU General Public License v3.0";
	public static final String LICENSE_INFO = "https://www.gnu.org/licenses/gpl-3.0.html";

	private Converter converter;
	private Gui gui;

	public static boolean isUnix() {
		String OS = System.getProperty("os.name")
				.toLowerCase();
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		try {
			assert false;
		} catch (java.lang.AssertionError e) {
			Loggger.log("assertions enabeled");
		}

		Material.init();
		Periphery.init();

		this.converter = new Converter();
		this.gui = new Gui(TITLE + " " + VERSION);

		new GuiLogic().run(this.gui);

		this.gui.setUponRun(() -> {
			this.saveNewPlace();

			File output = new File(this.gui.getOutputFile());
			ConverterData data = this.getConverterData();
			this.converter.convert(data, output);
			if (data.getUpdateTextures()) {
				final TexturePack pack = data.getTexturePack();
				if (!Steam.areTexturesUpToDate(data.getGame(), pack)) {
					TextureFolderMover.copyFolder(pack.getFolder(), data.getGame()
							.getMatriealPath(pack));
				}
			}

			data.getGame()
					.setGameTargetSavePath(output);
			Periphery.CONFIG.setConvertData(data);
			Periphery.CONFIG.setMinecraftPath(new File(this.gui.getMincraftPath()));
			Periphery.CONFIG.setSteamPath(this.gui.getSourcePath());
			Periphery.write();

			// result
			String outputLast = output.getName();
			this.gui.setDisplayedOutputName(outputLast);
			this.gui.setLabelOutputPath(output.getName());
			this.gui.setDisplayOpenFor(data.getGame()
					.getLongName());
			this.gui.setPanelResultVisible();
		});
	}

	private void saveNewPlace() {
		if (this.gui.getRememberPlaceSelected()) {
			String name = this.gui.getSaveLocation();
			if (name != null && name.length() > 0) {
				Loggger.log("Saving new place " + name + ".");
				Place place = this.gui.getPlaceFromCoordinates();
				Periphery.PLACES.addPlace(place);
			}
		} else {
			Place place = this.gui.getPlace();
			Place placeFromCoordinates = this.gui.getPlaceFromCoordinates();
			if (place != null) {
				place.setTo(placeFromCoordinates);
			}
		}
	}

	private void moveFolder(File materiaPath) {
		Object[] options = { "Cancel", "Copy" };
		String title = "Copy textures";
		String question = "This copies \"" + Periphery.CONFIG.getTexturePack() + "\"-textures to \n\"" + materiaPath
				+ "\\\"\n as desired by Valve's Hammer Editor.";
		int n = JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (n == 1) {
			File srcFolder = new File("textures" + File.separator + Periphery.CONFIG.getTexturePack());
			TextureFolderMover.moveFolderOld(materiaPath, srcFolder);
		}
	}

	public ConverterData getConverterData() {
		ConverterData converterData = new ConverterData();

		Place place = this.gui.getPlaceFromCoordinates();
		String worldName = this.gui.getWorld()
				.toString();
		Loggger.log("world = " + worldName);
		if (place == null) {
			Loggger.warn("Place not found");
		}
		place.setWorld(worldName);
		converterData.setPlace(place);

		converterData.setGame(this.gui.getSourceGame());

		// option
		String optionName = this.gui.getConvertOption();
		Loggger.log("option = " + optionName);
		ConvertOption option = Periphery.CONFIG.getConvertOption(optionName);
		converterData.setOption(option);

		// textures
		converterData.setTexturePack(TexturePack.getTexturePack(this.gui.getTexturePack()));
		converterData.setUpdateTextures(this.gui.getUpdateTextures());
		return converterData;
	}
}
