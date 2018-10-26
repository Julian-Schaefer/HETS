package org.cos30018.hets;

import javax.swing.JOptionPane;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.ui.HomeAgentController;

public class App {
	public static void main(String[] args) {
		try {
			JadeController jadeController = JadeController.getInstance();
			jadeController.launchPlattform();
			new HomeAgentController(jadeController.getHome());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to start HETS. Maybe it is already running?", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
