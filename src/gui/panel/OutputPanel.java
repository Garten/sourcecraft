package gui.panel;

import java.awt.EventQueue;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.google.common.util.concurrent.Runnables;

import basic.Loggger;
import gui.Gui;
import gui.SimpleTextFieldChangeListener;
import periphery.SourceGame;

public class OutputPanel extends JPanel {

	private static final String SOURCE_GAME = "Source Game";

	private static final String WILL_BE_OVERWRITTEN = "(Will be overwritten.)";

	private static final long serialVersionUID = -4352653661573645784L;

	private JComboBox<SourceGame> comboBox_SelectGame;
	private Consumer<String> uponSelectGame = input -> {
	};
	private Consumer<String> uponSelectGameIntern = gameName -> this.uponSelectGame.accept(gameName);

	private JTextField textField_OutputFile;

	private Consumer<String> buttonSelectOutputFile = input -> {
	};
	private Consumer<String> buttonSelectOutputFileIntern = input -> this.buttonSelectOutputFile.accept(input);

	private JLabel lbl_OutputExists;

	private Runnable uponEditOutput = Runnables.doNothing();
	private Runnable uponEditOutputIntern = () -> this.uponEditOutput.run();

	private Runnable uponContinue = Runnables.doNothing();
	private Runnable uponContinueIntern = () -> this.uponContinue.run();

	public OutputPanel() {
		this.initialize();
	}

	public void setPossibleGames(List<SourceGame> gameNames) {
		SourceGame[] type = new SourceGame[0];
		ComboBoxModel<SourceGame> games = new DefaultComboBoxModel<>(gameNames.toArray(type));
		this.comboBox_SelectGame.setModel(games);
	}

	public void setUponSelectedGame(Consumer<String> uponSelectGame) {
		this.uponSelectGame = uponSelectGame;
	}

	public void setButtonSelectOutputFile(Consumer<String> selectOutputFile) {
		this.buttonSelectOutputFile = selectOutputFile;
	}

	public void setOutputFileName(String outputFile) {
		this.textField_OutputFile.setText(outputFile);
	}

	public void setUponEditOutput(Runnable editOutput) {
		this.uponEditOutput = editOutput;
	}

	public void setOutputExistsVisibel(boolean visible) {
		this.lbl_OutputExists.setVisible(visible);
	}

	public String getOutputFile() {
		return this.textField_OutputFile.getText();
	}

	public SourceGame getSelectedGame() {
		return (SourceGame) this.comboBox_SelectGame.getSelectedItem();
	}

	public void setSelectedGame(SourceGame game) {
		this.comboBox_SelectGame.setSelectedItem(game);
	}

	public void setUponContinue(Runnable uponConitnue) {
		this.uponContinue = uponConitnue;
	}

	public static void main(String[] args) {
		Tester.main(args);
	}

	private static class Tester {

		private JFrame frame;

		public Tester() {
			this.initialize();
		}

		private void initialize() {
			this.frame = new JFrame();
			this.frame.setBounds(100, 100, 450, 700);
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.getContentPane()
					.add(new OutputPanel());
		}

		public static void main(String[] args) {
			EventQueue.invokeLater(() -> {
				try {
					Tester window = new Tester();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

	}

	/**
	 * Create the application.
	 *
	 * @wbp.parser.entryPoint
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JPanel panel_Output = this; // new JPanel();
		SpringLayout sl_panel_Output = new SpringLayout();
		panel_Output.setLayout(sl_panel_Output);

		JButton btnButton_OutputContinue = new JButton(Gui.CONTINUE_TEXT);
		this.configureContinueButton(btnButton_OutputContinue);
		btnButton_OutputContinue.addActionListener(arg0 -> this.uponContinueIntern.run());
		sl_panel_Output.putConstraint(SpringLayout.SOUTH, btnButton_OutputContinue, -10, SpringLayout.SOUTH,
				panel_Output);
		sl_panel_Output.putConstraint(SpringLayout.EAST, btnButton_OutputContinue, -10, SpringLayout.EAST,
				panel_Output);
		panel_Output.add(btnButton_OutputContinue);

		JLabel lbl_OutputFile = new JLabel("Output File");
		lbl_OutputFile.setFont(Gui.DEFAULT_FONT);
		panel_Output.add(lbl_OutputFile);

		this.textField_OutputFile = new JTextField();
		this.textField_OutputFile.setHorizontalAlignment(SwingConstants.LEFT);
		sl_panel_Output.putConstraint(SpringLayout.WEST, this.textField_OutputFile, 10, SpringLayout.WEST,
				panel_Output);
		sl_panel_Output.putConstraint(SpringLayout.EAST, this.textField_OutputFile, -10, SpringLayout.EAST,
				panel_Output);
		sl_panel_Output.putConstraint(SpringLayout.NORTH, this.textField_OutputFile, 6, SpringLayout.SOUTH,
				lbl_OutputFile);
		this.textField_OutputFile.setFont(Gui.DEFAULT_FONT);
		SimpleTextFieldChangeListener.addTo(this.textField_OutputFile, this.uponEditOutputIntern);
		panel_Output.add(this.textField_OutputFile);
		this.textField_OutputFile.setColumns(10);

		JButton btnNewButton_SelectOutputFile = new JButton(Gui.BROWSE_TEXT);
		sl_panel_Output.putConstraint(SpringLayout.WEST, btnNewButton_SelectOutputFile, -122, SpringLayout.EAST, this);
		Gui.configureBrowseButton(btnNewButton_SelectOutputFile);
		sl_panel_Output.putConstraint(SpringLayout.NORTH, btnNewButton_SelectOutputFile, 6, SpringLayout.SOUTH,
				this.textField_OutputFile);
		sl_panel_Output.putConstraint(SpringLayout.EAST, btnNewButton_SelectOutputFile, 0, SpringLayout.EAST,
				this.textField_OutputFile);
		btnNewButton_SelectOutputFile.setFont(Gui.DEFAULT_FONT);
		btnNewButton_SelectOutputFile.addActionListener(arg0 -> {
			String path = this.textField_OutputFile.getText();
			Loggger.log("path: " + path);
			this.buttonSelectOutputFileIntern.accept(path);
		});
		panel_Output.add(btnNewButton_SelectOutputFile);

		JLabel lbl_SourceGame = new JLabel(SOURCE_GAME);
		lbl_SourceGame.setFont(Gui.DEFAULT_FONT);
		sl_panel_Output.putConstraint(SpringLayout.WEST, lbl_OutputFile, 0, SpringLayout.WEST, lbl_SourceGame);
		sl_panel_Output.putConstraint(SpringLayout.NORTH, lbl_SourceGame, 10, SpringLayout.NORTH, panel_Output);
		sl_panel_Output.putConstraint(SpringLayout.WEST, lbl_SourceGame, 10, SpringLayout.WEST, panel_Output);
		panel_Output.add(lbl_SourceGame);

		this.comboBox_SelectGame = new JComboBox<>();
		sl_panel_Output.putConstraint(SpringLayout.EAST, this.comboBox_SelectGame, -10, SpringLayout.EAST,
				panel_Output);
		sl_panel_Output.putConstraint(SpringLayout.NORTH, lbl_OutputFile, Gui.SEPARATING_DISTANCE, SpringLayout.SOUTH,
				this.comboBox_SelectGame);
		this.comboBox_SelectGame.setFont(Gui.DEFAULT_FONT);
		this.comboBox_SelectGame.addActionListener(arg0 -> {
			String gameName = ((SourceGame) this.comboBox_SelectGame.getSelectedItem()).getLongName();
			this.uponSelectGameIntern.accept(gameName);
		});
		sl_panel_Output.putConstraint(SpringLayout.NORTH, this.comboBox_SelectGame, 6, SpringLayout.SOUTH,
				lbl_SourceGame);
		sl_panel_Output.putConstraint(SpringLayout.WEST, this.comboBox_SelectGame, 10, SpringLayout.WEST, panel_Output);
		panel_Output.add(this.comboBox_SelectGame);

		this.lbl_OutputExists = new JLabel(WILL_BE_OVERWRITTEN);
		sl_panel_Output.putConstraint(SpringLayout.NORTH, this.lbl_OutputExists, 6, SpringLayout.SOUTH,
				this.textField_OutputFile);
		sl_panel_Output.putConstraint(SpringLayout.WEST, this.lbl_OutputExists, 6, SpringLayout.WEST,
				this.textField_OutputFile);
		panel_Output.add(this.lbl_OutputExists);
		this.lbl_OutputExists.setVisible(false);

	}

	public void configureContinueButton(JButton button) {
		button.setPreferredSize(Gui.DIMENSION_OF_CONTINUE);
		button.setFont(Gui.FONT_CONTINUE);
	}

}
