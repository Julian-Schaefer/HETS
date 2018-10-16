package org.cos30018.hets.ui.home.dashboard;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;

public class PeriodControllerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6425404148702431224L;

	private Home home;

	public PeriodControllerPanel(Home home) {
		this.home = home;
		setUp();
	}

	private void setUp() {
		JButton nextPeriodButton = new JButton("Next period");
		nextPeriodButton.addActionListener((e) -> {
			home.nextPeriod();
		});
		add(nextPeriodButton);
	}
}
