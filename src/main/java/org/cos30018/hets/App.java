package org.cos30018.hets;

import javax.swing.JOptionPane;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.ui.HomeAgentController;

public class App {
	public static void main(String[] args) {
		try {
			boolean showGui = false;
			if (args.length > 0 && args[0].equals("show-jade-gui")) {
				showGui = true;
			}

			JadeController jadeController = JadeController.getInstance();
			jadeController.launchPlattform(showGui);
			new HomeAgentController(jadeController.getHome());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to start HETS. Maybe it is already running?", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
