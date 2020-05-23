package gui.panel;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.google.common.util.concurrent.Runnables;

import gui.Gui;

public class ResultPanel extends JPanel {

	private static final String FILE_OPEN_BASE_TEXT = "- Open file: ";
	private static final String OPEN_FILE = "Open File";
	private static final String UPDATE_TEXTURES = "Copy Textures";
	private static final String OPEN_HAMMER_EDITOR = "Open Hammer Editor";
	private static final long serialVersionUID = 3542905091002301486L;
	private JLabel lbl_OutputName;
	private Runnable uponShowFile = Runnables.doNothing();
	private Runnable uponShowFileIntern = () -> this.uponShowFile.run();
	private JLabel lbl_Texturesfound;
	private Runnable updateTextures = Runnables.doNothing();
	private Runnable updateTexturesIntern = () -> this.updateTextures.run();
	private Runnable openHammerEditor = Runnables.doNothing();
	private Runnable openHammerEditorIntern = () -> this.openHammerEditor.run();

	private JPanel panel_OpenHammerEditor;
	private JLabel lbl_Description1;
	private JLabel lbl_FileOpen;

	private JPanel panel_Result; // intern

	public ResultPanel() {
		this.initialize();
	}

	public void setDisplayedOutputName(String name) {
		this.lbl_OutputName.setText(name);
	}

	public void setDisplayOpenFor(String name) {
		this.lbl_Description1.setText("(for " + name + ")");
	}

	public void setUponShowFile(Runnable uponShowFile) {
		this.uponShowFile = uponShowFile;
	}

	public void setLabelTexturesFound(String text) {
		this.lbl_Texturesfound.setText(text);
	}

	public void setUponUpdateTextures(Runnable updateTextures) {
		this.updateTextures = updateTextures;
	}

//	public void setEnabledUpdateTextures(boolean enabled) {
//		this.btnNewButton_UpdateTextures.setEnabled(enabled);
//	}

	public void setOpenHammerEditor(Runnable openHammerEditor) {
		this.openHammerEditor = openHammerEditor;
	}

	public void setLabelOutputPath(String outputPath) {
		this.lbl_FileOpen.setText(FILE_OPEN_BASE_TEXT + outputPath);
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
			this.frame.setBounds(100, 100, 450, 300);
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.getContentPane()
					.add(new DetailsPanel());
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
		this.panel_Result = this; // new JPanel();
		// parent.addTab("Step 4: Details", null, panel_Details, null);
		SpringLayout springLayout = new SpringLayout();
		this.panel_Result.setLayout(springLayout);

		JLabel lblSuccessfullyConvertedTo = new JLabel("Successfully generated");
		springLayout.putConstraint(SpringLayout.EAST, lblSuccessfullyConvertedTo, -10, SpringLayout.EAST,
				this.panel_Result);
		lblSuccessfullyConvertedTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuccessfullyConvertedTo.setFont(Gui.FONT_SUCCESS);
		springLayout.putConstraint(SpringLayout.NORTH, lblSuccessfullyConvertedTo, 10, SpringLayout.NORTH,
				this.panel_Result);
		springLayout.putConstraint(SpringLayout.WEST, lblSuccessfullyConvertedTo, 10, SpringLayout.WEST,
				this.panel_Result);
		this.panel_Result.add(lblSuccessfullyConvertedTo);

		this.lbl_OutputName = new JLabel("OutputName");
		springLayout.putConstraint(SpringLayout.EAST, this.lbl_OutputName, -10, SpringLayout.EAST, this.panel_Result);
		this.lbl_OutputName.setHorizontalAlignment(SwingConstants.CENTER);
		this.lbl_OutputName.setFont(Gui.DEFAULT_FONT);
		springLayout.putConstraint(SpringLayout.NORTH, this.lbl_OutputName, 6, SpringLayout.SOUTH,
				lblSuccessfullyConvertedTo);
		springLayout.putConstraint(SpringLayout.WEST, this.lbl_OutputName, 0, SpringLayout.WEST,
				lblSuccessfullyConvertedTo);
		this.panel_Result.add(this.lbl_OutputName);

		JButton btn_ShowFile = new JButton(OPEN_FILE);
		springLayout.putConstraint(SpringLayout.SOUTH, btn_ShowFile, 0, SpringLayout.SOUTH, this.lbl_OutputName);
		btn_ShowFile.setFont(Gui.FONT_SMALL);
		btn_ShowFile.addActionListener(arg0 -> this.uponShowFileIntern.run());
		springLayout.putConstraint(SpringLayout.EAST, btn_ShowFile, -10, SpringLayout.EAST, this.panel_Result);
		this.panel_Result.add(btn_ShowFile);

		JLabel lbl_Textures = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lbl_Textures, 25, SpringLayout.SOUTH, btn_ShowFile);
		lbl_Textures.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.WEST, lbl_Textures, 10, SpringLayout.WEST, this.panel_Result);
		springLayout.putConstraint(SpringLayout.EAST, lbl_Textures, -10, SpringLayout.EAST, this.panel_Result);
		lbl_Textures.setFont(Gui.FONT_LARGE);
		this.panel_Result.add(lbl_Textures);

		this.lbl_Texturesfound = new JLabel("");
		this.lbl_Texturesfound.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.EAST, this.lbl_Texturesfound, -10, SpringLayout.EAST,
				this.panel_Result);
		this.lbl_Texturesfound.setFont(Gui.DEFAULT_FONT);
		springLayout.putConstraint(SpringLayout.NORTH, this.lbl_Texturesfound, 6, SpringLayout.SOUTH, lbl_Textures);
		springLayout.putConstraint(SpringLayout.WEST, this.lbl_Texturesfound, 0, SpringLayout.WEST,
				lblSuccessfullyConvertedTo);
		this.panel_Result.add(this.lbl_Texturesfound);

		this.panel_OpenHammerEditor = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, this.panel_OpenHammerEditor, 6, SpringLayout.SOUTH,
				this.lbl_OutputName);
		springLayout.putConstraint(SpringLayout.WEST, this.panel_OpenHammerEditor, 10, SpringLayout.WEST,
				this.panel_Result);
		springLayout.putConstraint(SpringLayout.EAST, this.panel_OpenHammerEditor, 0, SpringLayout.EAST,
				lblSuccessfullyConvertedTo);
		this.panel_Result.add(this.panel_OpenHammerEditor);

		this.lbl_Description1 = new JLabel("(for )");
		springLayout.putConstraint(SpringLayout.NORTH, this.lbl_Description1, 0, SpringLayout.SOUTH,
				this.panel_OpenHammerEditor);
		springLayout.putConstraint(SpringLayout.WEST, this.lbl_Description1, 0, SpringLayout.WEST,
				lblSuccessfullyConvertedTo);
		springLayout.putConstraint(SpringLayout.EAST, this.lbl_Description1, 0, SpringLayout.EAST,
				lblSuccessfullyConvertedTo);
		this.panel_OpenHammerEditor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btn_OpenHammerEditor = new JButton("Open Hammer Editor");
		this.panel_OpenHammerEditor.add(btn_OpenHammerEditor);
		btn_OpenHammerEditor.setPreferredSize(new Dimension(180, 45));
		springLayout.putConstraint(SpringLayout.NORTH, btn_OpenHammerEditor, 32, SpringLayout.SOUTH,
				this.lbl_Texturesfound);
		springLayout.putConstraint(SpringLayout.EAST, btn_OpenHammerEditor, 0, SpringLayout.EAST,
				lblSuccessfullyConvertedTo);
		btn_OpenHammerEditor.setFont(Gui.DEFAULT_FONT);
		btn_OpenHammerEditor.addActionListener(arg0 -> this.openHammerEditorIntern.run());
		this.lbl_Description1.setHorizontalAlignment(SwingConstants.CENTER);
		this.lbl_Description1.setFont(Gui.FONT_SMALL);
		this.panel_Result.add(this.lbl_Description1);

		JLabel lblNextSteps = new JLabel("Next steps in Hammer Editor:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNextSteps, 24, SpringLayout.SOUTH,
				this.panel_OpenHammerEditor);
		springLayout.putConstraint(SpringLayout.WEST, lblNextSteps, 0, SpringLayout.WEST, lblSuccessfullyConvertedTo);
		lblNextSteps.setFont(Gui.FONT_LARGE);
		this.add(lblNextSteps);

		this.lbl_FileOpen = new JLabel(FILE_OPEN_BASE_TEXT);
		springLayout.putConstraint(SpringLayout.NORTH, this.lbl_FileOpen, 6, SpringLayout.SOUTH, lblNextSteps);
		springLayout.putConstraint(SpringLayout.WEST, this.lbl_FileOpen, 0, SpringLayout.WEST,
				lblSuccessfullyConvertedTo);
		this.lbl_FileOpen.setFont(Gui.DEFAULT_FONT);
		this.panel_Result.add(this.lbl_FileOpen);

		JLabel lblConvertToBSP = new JLabel("- Convert to BSP (Press F9)");
		springLayout.putConstraint(SpringLayout.NORTH, lblConvertToBSP, 6, SpringLayout.SOUTH, this.lbl_FileOpen);
		springLayout.putConstraint(SpringLayout.WEST, lblConvertToBSP, 0, SpringLayout.WEST,
				lblSuccessfullyConvertedTo);
		lblConvertToBSP.setFont(Gui.DEFAULT_FONT);
		this.add(lblConvertToBSP);
	}

	public void configureContinueButton(JButton button) {
		button.setPreferredSize(Gui.DIMENSION_OF_CONTINUE);
		button.setFont(Gui.FONT_CONTINUE);
	}
}
