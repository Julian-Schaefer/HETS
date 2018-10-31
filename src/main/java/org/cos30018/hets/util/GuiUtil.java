package org.cos30018.hets.util;

import java.awt.Component;

import javax.swing.JPanel;

public class GuiUtil {

	public static void setPanelEnabled(JPanel panel, boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (Component component : components) {
			if (component instanceof JPanel) {
				setPanelEnabled((JPanel) component, isEnabled);
			}
			component.setEnabled(isEnabled);
		}
	}

}
