package org.cos30018.hets.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cos30018.hets.ui.custom.JPanelList;

public class GuiUtil {

	public static void setPanelEnabled(JPanel panel, boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (Component component : components) {
			if (component instanceof JPanel) {
				setPanelEnabled((JPanel) component, isEnabled);
			}

			if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component child = scrollPane.getViewport().getView();
				if (child instanceof JPanel) {
					setPanelEnabled((JPanelList) child, isEnabled);
				}
			}
			component.setEnabled(isEnabled);
		}
	}

	public static void setPanelBackground(JPanel panel, Color color) {
		panel.setBackground(color);

		Component[] components = panel.getComponents();

		for (Component component : components) {
			if (component instanceof JPanel) {
				setPanelBackground((JPanel) component, color);
			}

			if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component child = scrollPane.getViewport().getView();
				if (child instanceof JPanel) {
					setPanelBackground((JPanelList) child, color);
				}
			}
			component.setBackground(color);
		}
	}

}
