package gui;

import java.awt.Color;
import java.awt.Component;

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
		this.setText("<No Minecraft World Found>");
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
}
