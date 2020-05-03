package gui.panel;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.google.common.util.concurrent.Runnables;

import basic.RunnableWith;
import gui.Gui;
import gui.WorldComboboxRenderer;
import main.World;
import minecraft.Position;
import periphery.Place;

public class InputPanel extends JPanel {

	private static final String TO = "to";

	private static final String FROM = "from";

	private static final String X = "X";
	private static final String Y = "Y";
	private static final String Z = "Z";

	private static final String TEXT = "load";

	private static final long serialVersionUID = 3622857219240513029L;

	private SpringLayout sl_panel_Input;

	private JComboBox<World> comboBox_World;
	private JComboBox<LabeledCoordinates> comboBox_LabeledCoordinates;
	private JComboBox<Place> comboBox_Place;

	private JTextField textField_FromX;
	private JTextField textField_FromY;
	private JTextField textField_FromZ;
	private JTextField textField_ToX;
	private JTextField textField_ToY;
	private JTextField textField_ToZ;

	// private JFormattedTextField textField_FromX;
	// private JFormattedTextField textField_FromY;
	// private JFormattedTextField textField_FromZ;
	// private JFormattedTextField textField_ToX;
	// private JFormattedTextField textField_ToY;
	// private JFormattedTextField textField_ToZ;

	private JRadioButton rdbtn_RememberAs;
	private JTextField textField_RememberAs;

	private Runnable rememberAsToggle;
	// private RunnableWithArgument<Boolean> rememberAsToggleIntern = b ->
	// rememberAsToggle.run(b);

	private RunnableWith<LabeledCoordinates> loadLabeledCoordinates = player -> Runnables.doNothing();
	private RunnableWith<LabeledCoordinates> loadLabeledCoordinatesIntern = player -> this.loadLabeledCoordinates
			.run(player);

	private RunnableWith<Place> loadPlace = place -> Runnables.doNothing();
	private RunnableWith<Place> loadPlaceIntern = place -> this.loadPlace.run(place);

	private JButton btn_Delete;
	private RunnableWith<Place> deletePlace = place -> Runnables.doNothing();

	private Runnable uponContinue = Runnables.doNothing();

	private RunnableWith<World> uponWorldSelected = world -> Runnables.doNothing();

	public InputPanel() {
		this.initialize();
	}

	public void setUponContinue(Runnable uponContinue) {
		this.uponContinue = uponContinue;
	}

	@Override
	public SpringLayout getLayout() {
		return this.sl_panel_Input;
	}

	public void setPossibleWorlds(Vector<World> worldNames) {
		ComboBoxModel<World> worldOptions = new DefaultComboBoxModel<>(worldNames);
		this.comboBox_World.setModel(worldOptions);
	}

	public void setPossibleCoordinates(Vector<LabeledCoordinates> coordinateNames) {
		DefaultComboBoxModel<LabeledCoordinates> coordinatesOptions = new DefaultComboBoxModel<>(coordinateNames);
		this.comboBox_LabeledCoordinates.setModel(coordinatesOptions);
	}

	public void setPossiblePlaces(Vector<Place> placeSelectOptions) {
		DefaultComboBoxModel<Place> placeOptions = new DefaultComboBoxModel<>(placeSelectOptions);
		this.comboBox_Place.setModel(placeOptions);
	}

	public void setWorld(String worldName) {
		if (worldName != null) {
			World world = new World(worldName);
			this.comboBox_World.setSelectedItem(world);
		}
		this.uponWorldSelected.run((World) this.comboBox_World.getSelectedItem());
	}

	public World getWorld() {
		return (World) this.comboBox_World.getSelectedItem();
	}

	public Place getPlaceFromCoordinates() {
		String placeName = this.getSaveLocation();
		Place place = new Place(placeName);
		Object object = this.comboBox_World.getSelectedItem();
		if (object == null) {
			return null;
		}
		place.setWorld(object.toString());
		try {
			Position start = new Position(Integer.valueOf(this.textField_FromX.getText()),
					Integer.valueOf(this.textField_FromY.getText()), Integer.valueOf(this.textField_FromZ.getText()));
			place.setStart(start);
			Position end = new Position(Integer.valueOf(this.textField_ToX.getText()),
					Integer.valueOf(this.textField_ToY.getText()), Integer.valueOf(this.textField_ToZ.getText()));
			place.setEnd(end);
		} catch (NumberFormatException e) {
			return null;
		}
		return place;
	}

	public boolean getRememberPlaceSelected() {
		return this.rdbtn_RememberAs.isSelected();
	}

	public String getSaveLocation() {
		return this.textField_RememberAs.getText();
	}

	public Place getPlace() {
		return (Place) this.comboBox_Place.getSelectedItem();
	}

	public void setCoordinates(Place place) {
		this.textField_FromX.setText(place.getStart()
				.getX() + "");
		this.textField_FromY.setText(place.getStart()
				.getY() + "");
		this.textField_FromZ.setText(place.getStart()
				.getZ() + "");
		this.textField_ToX.setText(place.getEnd()
				.getX() + "");
		this.textField_ToY.setText(place.getEnd()
				.getY() + "");
		this.textField_ToZ.setText(place.getEnd()
				.getZ() + "");
	}

	public void setLoadCoordinates(RunnableWith<LabeledCoordinates> loadCoordinates) {
		this.loadLabeledCoordinates = loadCoordinates;
	}

	public void setLoadPlace(RunnableWith<Place> loadPlace) {
		this.loadPlace = loadPlace;
	}

	public void setUponWorldSelected(RunnableWith<World> uponWorldSelected) {
		this.uponWorldSelected = uponWorldSelected;
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
					.add(new InputPanel());
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
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setAllowsInvalid(false);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(Integer.MIN_VALUE);
		formatter.setMaximum(Integer.MAX_VALUE);

		JPanel panel_Input = this;
		this.sl_panel_Input = new SpringLayout();
		panel_Input.setLayout(this.sl_panel_Input);

		// continue button
		JButton btnButton_InputContinue = new JButton(Gui.CONTINUE_TEXT);
		this.configureContinueButton(btnButton_InputContinue);
		btnButton_InputContinue.addActionListener(arg -> this.uponContinue.run());
		this.sl_panel_Input.putConstraint(SpringLayout.SOUTH, btnButton_InputContinue, -10, SpringLayout.SOUTH,
				panel_Input);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, btnButton_InputContinue, -10, SpringLayout.EAST,
				panel_Input);
		panel_Input.add(btnButton_InputContinue);

		JLabel lbl_Minecraftworld = new JLabel("Minecraft-World");
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, lbl_Minecraftworld, 10, SpringLayout.NORTH, panel_Input);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, lbl_Minecraftworld, 10, SpringLayout.WEST, panel_Input);
		lbl_Minecraftworld.setFont(Gui.DEFAULT_FONT);
		panel_Input.add(lbl_Minecraftworld);

		this.comboBox_World = new JComboBox<>();
		this.comboBox_World
				.addActionListener(arg0 -> this.uponWorldSelected.run((World) this.comboBox_World.getSelectedItem()));
		this.comboBox_World.setRenderer(new WorldComboboxRenderer());
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.comboBox_World, 6, SpringLayout.SOUTH,
				lbl_Minecraftworld);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.comboBox_World, 0, SpringLayout.WEST,
				lbl_Minecraftworld);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, this.comboBox_World, -6, SpringLayout.EAST, this);
		this.comboBox_World.setFont(Gui.DEFAULT_FONT);
		panel_Input.add(this.comboBox_World);

		JLabel lbl_Coordinates = new JLabel("Coordinates");
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, lbl_Coordinates, 20, SpringLayout.SOUTH,
				this.comboBox_World);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, lbl_Coordinates, 10, SpringLayout.WEST, panel_Input);
		lbl_Coordinates.setFont(Gui.DEFAULT_FONT);
		panel_Input.add(lbl_Coordinates);

		this.comboBox_LabeledCoordinates = new JComboBox<>();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.comboBox_LabeledCoordinates, 6, SpringLayout.SOUTH,
				lbl_Coordinates);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.comboBox_LabeledCoordinates, 0, SpringLayout.WEST,
				lbl_Coordinates);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, this.comboBox_LabeledCoordinates, -6, SpringLayout.EAST,
				this);
		this.comboBox_LabeledCoordinates.setFont(Gui.DEFAULT_FONT);
		panel_Input.add(this.comboBox_LabeledCoordinates);

		JLabel lblNewLabel_From = new JLabel(InputPanel.FROM);
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, lblNewLabel_From, 10, SpringLayout.SOUTH,
				this.comboBox_LabeledCoordinates);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, lblNewLabel_From, 40, SpringLayout.WEST, this);
		panel_Input.add(lblNewLabel_From);

		this.textField_FromX = new JTextField();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.textField_FromX, 10, SpringLayout.SOUTH,
				lblNewLabel_From);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.textField_FromX, 0, SpringLayout.WEST,
				lblNewLabel_From);
		this.textField_FromX.setHorizontalAlignment(SwingConstants.TRAILING);
		this.textField_FromX.addActionListener(arg0 -> {
		});
		panel_Input.add(this.textField_FromX);
		this.textField_FromX.setColumns(10);

		this.textField_FromY = new JTextField();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.textField_FromY, 6, SpringLayout.SOUTH,
				this.textField_FromX);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.textField_FromY, 0, SpringLayout.WEST,
				this.textField_FromX);
		this.textField_FromY.setHorizontalAlignment(SwingConstants.TRAILING);
		this.textField_FromY.addActionListener(arg0 -> {
		});
		panel_Input.add(this.textField_FromY);
		this.textField_FromY.setColumns(10);

		this.textField_FromZ = new JTextField();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.textField_FromZ, 6, SpringLayout.SOUTH,
				this.textField_FromY);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.textField_FromZ, 0, SpringLayout.WEST,
				this.textField_FromX);
		this.textField_FromZ.setHorizontalAlignment(SwingConstants.TRAILING);
		this.textField_FromZ.addActionListener(arg0 -> {
		});
		panel_Input.add(this.textField_FromZ);
		this.textField_FromZ.setColumns(10);

		this.textField_ToX = new JTextField();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.textField_ToX, 0, SpringLayout.NORTH,
				this.textField_FromX);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.textField_ToX, 6, SpringLayout.EAST,
				this.textField_FromX);
		this.textField_ToX.setHorizontalAlignment(SwingConstants.TRAILING);
		this.textField_ToX.addActionListener(e -> {
		});

		JLabel lblNewLabel_To = new JLabel(TO);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, lblNewLabel_To, 0, SpringLayout.WEST, this.textField_ToX);
		this.sl_panel_Input.putConstraint(SpringLayout.SOUTH, lblNewLabel_To, 0, SpringLayout.SOUTH, lblNewLabel_From);
		panel_Input.add(lblNewLabel_To);
		panel_Input.add(this.textField_ToX);
		this.textField_ToX.setColumns(10);

		this.textField_ToY = new JTextField();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.textField_ToY, 0, SpringLayout.NORTH,
				this.textField_FromY);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.textField_ToY, 6, SpringLayout.EAST,
				this.textField_FromY);
		this.textField_ToY.setHorizontalAlignment(SwingConstants.TRAILING);
		this.textField_ToY.addActionListener(e -> {
		});
		panel_Input.add(this.textField_ToY);
		this.textField_ToY.setColumns(10);

		this.textField_ToZ = new JTextField();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.textField_ToZ, 0, SpringLayout.NORTH,
				this.textField_FromZ);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.textField_ToZ, 6, SpringLayout.EAST,
				this.textField_FromZ);
		this.textField_ToZ.setHorizontalAlignment(SwingConstants.TRAILING);
		this.textField_ToZ.addActionListener(e -> {
		});
		panel_Input.add(this.textField_ToZ);
		this.textField_ToZ.setColumns(10);

		JLabel lbl_X = new JLabel(X);
		this.sl_panel_Input.putConstraint(SpringLayout.SOUTH, lbl_X, -6, SpringLayout.SOUTH, this.textField_FromX);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, lbl_X, -6, SpringLayout.WEST, this.textField_FromX);
		panel_Input.add(lbl_X);

		JLabel lbl_Y = new JLabel(Y);
		this.sl_panel_Input.putConstraint(SpringLayout.SOUTH, lbl_Y, -6, SpringLayout.SOUTH, this.textField_FromY);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, lbl_Y, -6, SpringLayout.WEST, this.textField_FromY);
		panel_Input.add(lbl_Y);

		JLabel lbl_Z = new JLabel(Z);
		this.sl_panel_Input.putConstraint(SpringLayout.SOUTH, lbl_Z, -6, SpringLayout.SOUTH, this.textField_FromZ);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, lbl_Z, -6, SpringLayout.WEST, this.textField_FromZ);
		panel_Input.add(lbl_Z);

		JPanel panel_LoadSave = new JPanel();
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, panel_LoadSave, 20, SpringLayout.SOUTH,
				this.textField_FromZ);
		this.sl_panel_Input.putConstraint(SpringLayout.SOUTH, panel_LoadSave, -6, SpringLayout.SOUTH, panel_Input);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, panel_LoadSave, 0, SpringLayout.WEST, lbl_Minecraftworld);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, panel_LoadSave, -6, SpringLayout.EAST, panel_Input);
		panel_Input.add(panel_LoadSave);
		panel_LoadSave.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_Save = new JPanel();
		panel_LoadSave.add(panel_Save);
		SpringLayout sl_panel_Save = new SpringLayout();
		panel_Save.setLayout(sl_panel_Save);

		this.rdbtn_RememberAs = new JRadioButton("remember as");
		this.rememberAsToggle = () -> {
			boolean selected = this.rdbtn_RememberAs.isSelected();
			this.textField_RememberAs.setEnabled(selected);
		};
		this.rdbtn_RememberAs.addActionListener(arg0 -> {
			this.rememberAsToggle.run();
		});
		sl_panel_Save.putConstraint(SpringLayout.NORTH, this.rdbtn_RememberAs, 10, SpringLayout.NORTH, panel_Save);
		sl_panel_Save.putConstraint(SpringLayout.WEST, this.rdbtn_RememberAs, 10, SpringLayout.WEST, panel_Save);
		this.rdbtn_RememberAs.setFont(Gui.DEFAULT_FONT);
		panel_Save.add(this.rdbtn_RememberAs);

		this.textField_RememberAs = new JTextField();
		sl_panel_Save.putConstraint(SpringLayout.NORTH, this.textField_RememberAs, 6, SpringLayout.SOUTH,
				this.rdbtn_RememberAs);
		sl_panel_Save.putConstraint(SpringLayout.WEST, this.textField_RememberAs, 10, SpringLayout.WEST, panel_Save);
		sl_panel_Save.putConstraint(SpringLayout.EAST, this.textField_RememberAs, -10, SpringLayout.EAST, panel_Save);
		this.textField_RememberAs.setFont(Gui.DEFAULT_FONT);
		panel_Save.add(this.textField_RememberAs);
		this.textField_RememberAs.setColumns(10);

		// begin load panel
		JPanel panel_Load = new JPanel();
		panel_LoadSave.add(panel_Load);
		SpringLayout sl_panel_Load = new SpringLayout();
		panel_Load.setLayout(sl_panel_Load);

		this.comboBox_Place = new JComboBox<>();
		sl_panel_Load.putConstraint(SpringLayout.EAST, this.comboBox_Place, -10, SpringLayout.EAST, panel_Load);
		this.sl_panel_Input.putConstraint(SpringLayout.NORTH, this.comboBox_Place, 6, SpringLayout.SOUTH, panel_Load);
		this.sl_panel_Input.putConstraint(SpringLayout.WEST, this.comboBox_Place, 6, SpringLayout.EAST, panel_Load);
		this.sl_panel_Input.putConstraint(SpringLayout.EAST, this.comboBox_Place, 6, SpringLayout.WEST, panel_Load);
		panel_Load.add(this.comboBox_Place);
		this.comboBox_Place.setFont(Gui.DEFAULT_FONT);

		JLabel lbl_Load = new JLabel(TEXT);
		sl_panel_Load.putConstraint(SpringLayout.WEST, lbl_Load, 10, SpringLayout.WEST, panel_Load);
		sl_panel_Load.putConstraint(SpringLayout.NORTH, this.comboBox_Place, 6, SpringLayout.SOUTH, lbl_Load);
		sl_panel_Load.putConstraint(SpringLayout.WEST, this.comboBox_Place, 0, SpringLayout.WEST, lbl_Load);
		sl_panel_Load.putConstraint(SpringLayout.NORTH, lbl_Load, 10, SpringLayout.NORTH, panel_Load);
		lbl_Load.setFont(Gui.DEFAULT_FONT);
		panel_Load.add(lbl_Load);

		this.btn_Delete = new JButton("delete");
		sl_panel_Load.putConstraint(SpringLayout.NORTH, this.btn_Delete, 6, SpringLayout.SOUTH, this.comboBox_Place);
		sl_panel_Load.putConstraint(SpringLayout.EAST, this.btn_Delete, 0, SpringLayout.EAST, this.comboBox_Place);
		this.btn_Delete.addActionListener(arg -> {
			Place place = this.getPlace();
			this.deletePlace.run(place);
		});
		panel_Load.add(this.btn_Delete);

		this.comboBox_Place.addActionListener(arg0 -> {
			Place selectedItem = (Place) this.comboBox_Place.getSelectedItem();
			// Place place = selectedItem.getPlace();
			this.loadPlaceIntern.run(selectedItem);
		});

		this.comboBox_LabeledCoordinates.addActionListener(arg0 -> {
			LabeledCoordinates selectedItem = (LabeledCoordinates) this.comboBox_LabeledCoordinates.getSelectedItem();
			this.loadLabeledCoordinatesIntern.run(selectedItem);
		});
		// end load panel

		this.rememberAsToggle.run();
	}

	public void configureContinueButton(JButton button) {
		button.setPreferredSize(Gui.DIMENSION_OF_CONTINUE);
		button.setFont(Gui.FONT_CONTINUE);
	}

	public void setDeletePlace(RunnableWith<Place> deletePlace) {
		this.deletePlace = deletePlace;
	}

	public void setLabeledCoordinates(LabeledCoordinates labeledCoordinates) {
		this.comboBox_LabeledCoordinates.setSelectedItem(labeledCoordinates);
	}
}
