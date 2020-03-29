package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import basic.RunnableWith;
import basic.Validator;
import gui.panel.DetailsPanel;
import gui.panel.InputPanel;
import gui.panel.LabeledCoordinates;
import gui.panel.OutputPanel;
import gui.panel.ResultPanel;
import gui.panel.SetupPanel;
import main.World;
import periphery.Place;
import periphery.SourceGame;

public class Gui {

	private static final String RESULT = "Result";
	private static final String DETAILS = "Details";
	private static final String OUTPUT = "Output";
	private static final String INPUT = "Input";
	private static final String SETUP = "Setup";

	private static final int WIDTH = 520;
	private static final int HEIGHT = 560;

	private String title;

	private JTabbedPane tabbedPane;

	private JFrame frmTesttitle;

	// input
	private Validator<Place> validatePlace = Validator.PLACE_ACCEPTOR;
	private Validator<Place> validatePlaceIntern = place -> this.validatePlace.run(place);

	// details

	// result

	// new
	private SetupPanel setupPanel;
	private InputPanel inputPanel;
	private OutputPanel outputPanel;
	private DetailsPanel detailsPanel;
	private ResultPanel resultPanel;

	public static int fontSizeSuccess = 18;
	public static int fontSizeLarge = 16;
	public static int fontSize = 14;
	public static int fontSizeSmall = 12;
	public static final String FONTTYPE = "Dialog";
	public static final Font FONT_CONTINUE = new Font(FONTTYPE, Font.BOLD, fontSize);
	public static final Font FONT_SMALL = new Font(FONTTYPE, Font.PLAIN, fontSizeSmall);
	public static final Font FONT_SUCCESS = new Font(FONTTYPE, Font.PLAIN, fontSizeSuccess);
	public static final Font FONT_LARGE = new Font(FONTTYPE, Font.PLAIN, fontSizeLarge);
	public static final Font DEFAULT_FONT = new Font(FONTTYPE, Font.PLAIN, fontSize);
	public static final String BROWSE_TEXT = "Browse";
	public static final String CONTINUE_TEXT = "Continue";
	public static final Dimension DIMENSION_OF_BROWSE = new Dimension(120, 30);
	public static final Dimension DIMENSION_OF_CONTINUE = new Dimension(120, 40);
	public static final int SEPARATING_DISTANCE = 25;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Main.main(args);
		EventQueue.invokeLater(() -> {
			try {
				new Gui("Test");
				// this.frmTesttitle.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 *
	 * @wbp.parser.entryPoint
	 */
	public Gui(String title) {
		this.title = title;
		this.initialize();
		this.frmTesttitle.setVisible(true);
	}

	// methods ontop

	public void setPlace(Place place) {
		if (place != null) {
			this.setWorld(place.getWorld());
			this.setCoordinates(place);
		}
	}

	public void setValidatePlace(Validator<Place> validatePlace) {
		this.validatePlace = validatePlace;
	}

	// setup
	public void setEditedTextFieldMinecraftPath(Runnable editedTextFieldMinecraftPath) {
		this.setupPanel.setEditedTextFieldMinecraftPath(editedTextFieldMinecraftPath);
	}

	public void setEditedTextFieldSourcePath(Runnable editedTextFieldSourcePath) {
		this.setupPanel.setEditedTextFieldSourcePath(editedTextFieldSourcePath);
	}

	public void setLabelIssourcesdkinstalled(String text) {
		this.setupPanel.setLabelIssourcesdkinstalled(text);
	}

	public void setVisibilityLabelIssourcesdkinstalled(boolean visible) {
		this.setupPanel.setVisibilityLabelIssourcesdkinstalled(visible);
	}

	public void setEnabledButtonContinueAtSetup(boolean enabled) {
		this.setupPanel.setEnabledButtonContinueAtSetup(enabled);
		this.tabbedPane.setEnabledAt(1, enabled);
		this.tabbedPane.setEnabledAt(2, enabled);
		this.tabbedPane.setEnabledAt(3, enabled);
	}

	public void setMinecraftPath(File path) {
		this.setupPanel.setMinecraftPath(path.toString());
	}

	public String getMincraftPath() {
		return this.setupPanel.getMincraftPath();
	}

	public void setSourcePath(File file) {
		this.setupPanel.setSourcePath(file.toString());
	}

	public File getSourcePath() {
		return new File(this.setupPanel.getSourcePath());
	}

	public void setSelectMinecraftPath(RunnableWith<String> selectMinecraftPath) {
		this.setupPanel.setSelectMinecraftPath(selectMinecraftPath);
	}

	public void setSelectSourcePath(RunnableWith<String> selectSourcePath) {
		this.setupPanel.setSelectSourcePath(selectSourcePath);
	}

	public void setTextFieldMincraftPathColor(Color color) {
		this.setupPanel.setTextFieldMincraftPathColor(color);
	}

	public void setTextFieldSteamPathColor(Color color) {
		this.setupPanel.setTextFieldSteamPathColor(color);
	}

	// input
	public void setPossibleWorlds(Vector<World> worldNames) {
		this.inputPanel.setPossibleWorlds(worldNames);
	}

	public void setPossibleCoordinates(Vector<LabeledCoordinates> coordinateNames) {
		this.inputPanel.setPossibleCoordinates(coordinateNames);
	}

	public void setPossiblePlaces(Vector<Place> placeSelectOptions) {
		this.inputPanel.setPossiblePlaces(placeSelectOptions);
	}

	public void setLoadCoordinates(RunnableWith<LabeledCoordinates> loadCoordinates) {
		this.inputPanel.setLoadCoordinates(loadCoordinates);
	}

	public void setLoadPlace(RunnableWith<Place> loadPlace) {
		this.inputPanel.setLoadPlace(loadPlace);
	}

	public void setUponWorldSelected(RunnableWith<World> uponWorldSelected) {
		this.inputPanel.setUponWorldSelected(uponWorldSelected);
	}

	public void setWorld(String world) {
		this.inputPanel.setWorld(world);
	}

	public World getWorld() {
		return this.inputPanel.getWorld();
	}

	public boolean getRememberPlaceSelected() {
		return this.inputPanel.getRememberPlaceSelected();
	}

	public String getSaveLocation() {
		return this.inputPanel.getSaveLocation();
	}

	public void setCoordinates(Place place) {
		this.inputPanel.setCoordinates(place);
	}

	public Place getPlaceFromCoordinates() {
		return this.inputPanel.getPlaceFromCoordinates();
	}

	public Place getPlace() {
		Place place = this.inputPanel.getPlace();
		if (place == null) {
			return new Place();
		}
		return this.inputPanel.getPlace();
	}

	public void setDeletePlace(RunnableWith<Place> deletePlace) {
		this.inputPanel.setDeletePlace(deletePlace);
	}

	// output // delegate
	public void setPossibleGames(List<SourceGame> gameNames) {
		this.outputPanel.setPossibleGames(gameNames);
	}

	public void setOutputFileName(String outputFile) {
		this.outputPanel.setOutputFileName(outputFile);
	}

	public void setUponSelectedGame(RunnableWith<String> uponSelectGame) {
		this.outputPanel.setUponSelectedGame(uponSelectGame);
	}

	public void setButtonSelectOutputFile(RunnableWith<String> selectOutputFile) {
		this.outputPanel.setButtonSelectOutputFile(selectOutputFile);
	}

	public void setUponEditOutput(Runnable editOutput) {
		this.outputPanel.setUponEditOutput(editOutput);
	}

	public void setOutputExistsVisibel(boolean visible) {
		this.outputPanel.setOutputExistsVisibel(visible);
	}

	public SourceGame getSourceGame() {
		return this.outputPanel.getSourceGame();
	}

	public String getOutputFile() {
		return this.outputPanel.getOutputFile();
	}

	// details
	public void setUponRun(Runnable run) {
		this.detailsPanel.setRunRunnable(run); // delegate
	}

	public boolean getUpdateTextures() {
		return this.detailsPanel.getUpdateTextures();
	}

	public void setVisibleTextTexturesUpToDate(boolean visible) {
		this.detailsPanel.setVisibleTextTexturesUpToDate(visible);
	}

	// result
	public void setPanelResultVisible() {
		this.tabbedPane.addTab(RESULT, null, this.resultPanel, null);
		this.tabbedPane.setSelectedComponent(this.resultPanel);
		// panel_Result.setVisible(visible);
	}

	public void setDisplayedOutputName(String name) {
		this.resultPanel.setDisplayedOutputName(name);
	}

	public void setUponShowFile(Runnable uponShowFile) {
		this.resultPanel.setUponShowFile(uponShowFile);
	}

	public void setLabelTexturesFound(String text) {
		this.resultPanel.setLabelTexturesFound(text);
	}

	public void setUponUpdateTextures(Runnable updateTextures) {
		this.resultPanel.setUponUpdateTextures(updateTextures);
	}

//	public void setEnabledUpdateTextures(boolean enabled) {
//		this.resultPanel.setEnabledUpdateTextures(enabled);
//	}

	public void setOpenHammerEditor(Runnable openHammerEditor) {
		this.resultPanel.setOpenHammerEditor(openHammerEditor);
	}

	public void setLabelOutputPath(String outputPath) {
		this.resultPanel.setLabelOutputPath(outputPath);
	}

	private void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("control", Color.WHITE); // overall background
					break;
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setLookAndFeel();

		this.frmTesttitle = new JFrame();
		this.frmTesttitle.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(".\\resources\\hammer_icon.jpg"));
		this.frmTesttitle.setTitle(this.title);
		this.frmTesttitle.getContentPane()
				.setFont(Gui.DEFAULT_FONT);
		this.frmTesttitle.setBounds(100, 100, WIDTH, HEIGHT);
		this.frmTesttitle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		this.tabbedPane.setBorder(null);
		this.tabbedPane.setFont(Gui.DEFAULT_FONT);
		this.tabbedPane.addChangeListener(arg0 -> {
			//
		});
		final JTabbedPane tabbedPaneFinal = this.tabbedPane;
		this.frmTesttitle.getContentPane()
				.add(this.tabbedPane, BorderLayout.CENTER);

		// setup Panel
		this.setupPanel = new SetupPanel();
		this.setupPanel.setUponContinue(() -> {
			tabbedPaneFinal.setSelectedIndex(1); // index of input
		});
		this.tabbedPane.addTab(SETUP, null, this.setupPanel, null);

		// input Panel
		this.inputPanel = new InputPanel();
		this.inputPanel.setUponContinue(() -> {
			if (this.validatePlaceIntern.run(this.inputPanel.getPlaceFromCoordinates())) {
				tabbedPaneFinal.setSelectedIndex(2);
			}
		});
		this.tabbedPane.addTab(INPUT, null, this.inputPanel, null);

		// output Panel
		this.outputPanel = new OutputPanel();
		this.outputPanel.setUponContinue(() -> {
			tabbedPaneFinal.setSelectedIndex(3); // index of details
		});
		this.tabbedPane.addTab(OUTPUT, null, this.outputPanel, null);

		// details Panel
		this.detailsPanel = new DetailsPanel();
		this.tabbedPane.addTab(DETAILS, null, this.detailsPanel, null);

		// result
		this.resultPanel = new ResultPanel();
		this.tabbedPane.addTab(RESULT, null, this.resultPanel, null);
		this.tabbedPane.setEnabledAt(4, false);
	}

	public static void configureContinueButton(JButton button) {
		button.setPreferredSize(Gui.DIMENSION_OF_CONTINUE);
		button.setFont(Gui.FONT_CONTINUE);
	}

	public String getConvertOption() {
		return this.detailsPanel.getConvertOption();
	}

	public void setPossibleConverterOptions(String[] convertOptionNames) {
		this.detailsPanel.setPossibleConverterOptions(convertOptionNames);
	}

	public void setPossibleTextures(String[] textureNames) {
		this.detailsPanel.setPossibleTextures(textureNames);
	}

	public void setDisplayOpenFor(String name) {
		this.resultPanel.setDisplayOpenFor(name);
	}

	public void setLabeledCoordinates(LabeledCoordinates labeledCoordinates) {
		this.inputPanel.setLabeledCoordinates(labeledCoordinates);
	}

	public void setSelectedTexturePack(String texturePack) {
		this.detailsPanel.setTexturePack(texturePack);
	}

	public void setUponSelectTexturePack(RunnableWith<String> uponSelectTexturePack) {
		this.detailsPanel.setUponSelectTexturePack(uponSelectTexturePack);
	}

	public static final void configureBrowseButton(JButton button) {
		button.setSize(120, 30);
	}

	public String getTexturePack() {
		return this.detailsPanel.getTexturePack();
	}
}
