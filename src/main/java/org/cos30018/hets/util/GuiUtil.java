package org.cos30018.hets.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.ui.custom.JPanelList;
import org.cos30018.hets.ui.custom.button.StyledJPanelUI;

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

	public static void setComponentsInPanelEnabled(JPanel panel, boolean isEnabled) {
		Component[] components = panel.getComponents();

		for (Component component : components) {
			if (component instanceof JPanel) {
				setComponentsInPanelEnabled((JPanel) component, isEnabled);
			}

			if (component instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) component;
				Component child = scrollPane.getViewport().getView();
				if (child instanceof JPanel) {
					setComponentsInPanelEnabled((JPanelList) child, isEnabled);
				}
			}

			if (component instanceof JTextField) {
				JTextField textField = (JTextField) component;
				textField.setEditable(isEnabled);
			}

			if (component instanceof JComboBox<?>) {
				JComboBox<?> comboxBox = (JComboBox<?>) component;
				comboxBox.setEnabled(isEnabled);
			}
		}
	}

	public static JPanel getCardPanel(String title, JPanel child) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setUI(new StyledJPanelUI());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JLabel titleLbl = new JLabel(title);
		titleLbl.setHorizontalAlignment(JLabel.LEFT);
		panel.add(titleLbl, BorderLayout.NORTH);

		JPanel cardPanel = new JPanel(new BorderLayout());
		cardPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		cardPanel.add(child);
		panel.add(cardPanel);

		GuiUtil.setPanelBackground(panel, Color.WHITE);
		return panel;
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
