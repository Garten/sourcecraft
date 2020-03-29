package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import main.World;

public class WorldComboboxRenderer extends JLabel implements ListCellRenderer<Object> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1184215949297548461L;

	private static final Color NIMBUS_FOCUS = new Color(51, 98, 140);

	private Font uhOhFont;

	public WorldComboboxRenderer() {
		// setOpaque(true);
		this.setVerticalAlignment(CENTER);
	}

	/*
	 * This method finds the image and text corresponding to the selected value and
	 * returns the label, set up to display the text and image.
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// int selectedIndex = ((Integer) value).intValue();

		// Set the icon and text. If icon was null, say so.
		// ImageIcon icon = images[selectedIndex];
		// String pet = petStrings[selectedIndex];
		this.setText("<no world found>");
		if (value instanceof World) {
			World world = (World) value;
			this.setIcon(world.getIcon());
			this.setText(world.toString());
		}
		if (isSelected) {
			this.setForeground(Color.WHITE);
			this.setBackground(NIMBUS_FOCUS);
		} else {
			this.setForeground(Color.BLACK);
			this.setBackground(Color.WHITE);
		}

		return this;
	}

	// Set the font and text when no image was found.
	protected void setUhOhText(String uhOhText, Font normalFont) {
		if (this.uhOhFont == null) { // lazily create this font
			this.uhOhFont = normalFont.deriveFont(Font.ITALIC);
		}
		this.setFont(this.uhOhFont);
		this.setText(uhOhText);
	}

	protected static ImageIcon createImageIcon() { // TODO
		String path = "images//test.jpg";
		ImageIcon icon = new ImageIcon(path);
		return icon = new ImageIcon(icon.getImage()
				.getScaledInstance(25, 25, 0));
	}
}
