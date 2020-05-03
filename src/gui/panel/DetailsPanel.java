package gui.panel;

import java.awt.EventQueue;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.google.common.util.concurrent.Runnables;

import basic.RunnableWith;
import gui.Gui;

public class DetailsPanel extends JPanel {

	private static final String TEXTURES_ARE_UP_TO_DATE = "Textures are in the correct folder for Hammer Editor";
	private static final String RUN = "Run";
	private static final String DETAILS = "Details";
	private static final String TEXTURES = "Textures";
	private static final String COPY_TEXTURES_FOR_HAMMER_EDITOR = "Copy textures to correct folder for Hammer Editor";

	private static final long serialVersionUID = -8160992679922330899L;

	private JComboBox<String> comboBox_Textures;
	private JComboBox<String> comboBox_ConverterOptions;
	private JCheckBox chckbx_CopyTextures;

	private RunnableWith<String> uponSelectTexturePack = place -> Runnables.doNothing();
	private RunnableWith<String> uponSelectTexturePackIntern = texturePack -> this.uponSelectTexturePack
			.run(texturePack);

	private Runnable run = Runnables.doNothing();

//	private JPanel panel_Details; // intern
	private JLabel lbl_TexturesUpToDate;

	public DetailsPanel() {
		this.initialize();
	}

	public void setTexturePack(String texturePack) {
		this.comboBox_Textures.setSelectedItem(texturePack);
	}

	public void setUponSelectTexturePack(RunnableWith<String> uponSelectTexturePack) {
		this.uponSelectTexturePack = uponSelectTexturePack;
	}

	public boolean getUpdateTextures() {
		return this.chckbx_CopyTextures.isSelected();
	}

	public String getTexturePack() {
		return (String) this.comboBox_Textures.getSelectedItem();
	}

	public void setPossibleTextures(String[] textureNames) {
		if (textureNames == null) {
			return;
		}
		ComboBoxModel<String> model = new DefaultComboBoxModel<>(textureNames);
		this.comboBox_Textures.setModel(model);
	}

	public void setPossibleConverterOptions(String[] convertOptionNames) {
		ComboBoxModel<String> model = new DefaultComboBoxModel<>(convertOptionNames);
		this.comboBox_ConverterOptions.setModel(model);
	}

	public void setSelectedConverterOptions(String convertOption) {
		this.comboBox_ConverterOptions.setSelectedItem(convertOption);
	}

	public String getConvertOption() {
		return (String) this.comboBox_ConverterOptions.getSelectedItem();
	}

	public void setRunRunnable(Runnable run) {
		this.run = run;
	}

	public void setVisibleTextTexturesUpToDate(boolean visible) {
//		this.lbl_TexturesUpToDate.setVisible(visible);
		this.chckbx_CopyTextures.setVisible(!visible);
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
		JPanel panel_Details = this;
		SpringLayout sl_panel_Details = new SpringLayout();
		panel_Details.setLayout(sl_panel_Details);

		JButton btnNewButton_Run = new JButton(RUN);
		btnNewButton_Run.addActionListener(arg0 -> this.run.run());
		sl_panel_Details.putConstraint(SpringLayout.SOUTH, btnNewButton_Run, -10, SpringLayout.SOUTH, panel_Details);
		sl_panel_Details.putConstraint(SpringLayout.EAST, btnNewButton_Run, -10, SpringLayout.EAST, panel_Details);
		Gui.configureContinueButton(btnNewButton_Run);
		panel_Details.add(btnNewButton_Run);

		this.comboBox_ConverterOptions = new JComboBox<>();
		sl_panel_Details.putConstraint(SpringLayout.WEST, this.comboBox_ConverterOptions, 10, SpringLayout.WEST,
				panel_Details);
		sl_panel_Details.putConstraint(SpringLayout.EAST, this.comboBox_ConverterOptions, -10, SpringLayout.EAST,
				panel_Details);
		this.comboBox_ConverterOptions.setFont(Gui.DEFAULT_FONT);
		panel_Details.add(this.comboBox_ConverterOptions);

		JLabel lbl_Details = new JLabel(DETAILS);
		sl_panel_Details.putConstraint(SpringLayout.NORTH, this.comboBox_ConverterOptions, 6, SpringLayout.SOUTH,
				lbl_Details);
		sl_panel_Details.putConstraint(SpringLayout.WEST, lbl_Details, 10, SpringLayout.WEST, panel_Details);
		sl_panel_Details.putConstraint(SpringLayout.NORTH, lbl_Details, 10, SpringLayout.NORTH, panel_Details);
		lbl_Details.setFont(Gui.DEFAULT_FONT);
		panel_Details.add(lbl_Details);

		JLabel lbl_Details_Textures = new JLabel(TEXTURES);
		sl_panel_Details.putConstraint(SpringLayout.NORTH, lbl_Details_Textures, 25, SpringLayout.SOUTH,
				this.comboBox_ConverterOptions);
		sl_panel_Details.putConstraint(SpringLayout.WEST, lbl_Details_Textures, 0, SpringLayout.WEST,
				this.comboBox_ConverterOptions);
		lbl_Details_Textures.setFont(Gui.DEFAULT_FONT);
		panel_Details.add(lbl_Details_Textures);

		this.comboBox_Textures = new JComboBox<>();
		sl_panel_Details.putConstraint(SpringLayout.NORTH, this.comboBox_Textures, 6, SpringLayout.SOUTH,
				lbl_Details_Textures);
		sl_panel_Details.putConstraint(SpringLayout.WEST, this.comboBox_Textures, 0, SpringLayout.WEST,
				this.comboBox_ConverterOptions);
		sl_panel_Details.putConstraint(SpringLayout.EAST, this.comboBox_Textures, -10, SpringLayout.EAST,
				panel_Details);
		this.comboBox_Textures.setFont(Gui.DEFAULT_FONT);
		panel_Details.add(this.comboBox_Textures);

		this.chckbx_CopyTextures = new JCheckBox(COPY_TEXTURES_FOR_HAMMER_EDITOR);
		this.chckbx_CopyTextures.setFont(Gui.DEFAULT_FONT);
		this.chckbx_CopyTextures.setSelected(true);
		sl_panel_Details.putConstraint(SpringLayout.NORTH, this.chckbx_CopyTextures, 6, SpringLayout.SOUTH,
				this.comboBox_Textures);
		sl_panel_Details.putConstraint(SpringLayout.WEST, this.chckbx_CopyTextures, 10, SpringLayout.WEST, this);
		panel_Details.add(this.chckbx_CopyTextures);

		this.lbl_TexturesUpToDate = new JLabel(TEXTURES_ARE_UP_TO_DATE);
		sl_panel_Details.putConstraint(SpringLayout.NORTH, this.lbl_TexturesUpToDate, 6, SpringLayout.SOUTH,
				this.chckbx_CopyTextures);
		sl_panel_Details.putConstraint(SpringLayout.WEST, this.lbl_TexturesUpToDate, 0, SpringLayout.WEST,
				this.comboBox_ConverterOptions);
		this.lbl_TexturesUpToDate.setFont(Gui.DEFAULT_FONT);
		this.lbl_TexturesUpToDate.setVisible(false);
		panel_Details.add(this.lbl_TexturesUpToDate);

		this.comboBox_Textures.addActionListener(arg0 -> {
			String texturePack = (String) this.comboBox_Textures.getSelectedItem();
			this.uponSelectTexturePackIntern.run(texturePack);
		});
	}
}
