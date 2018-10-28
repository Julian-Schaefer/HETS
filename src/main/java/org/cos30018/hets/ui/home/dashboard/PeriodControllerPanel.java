package org.cos30018.hets.ui.home.dashboard;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledButtonUI;
import org.cos30018.hets.ui.custom.button.StyledRoundButtonUI;

import java.awt.*;

public class PeriodControllerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6425404148702431224L;

	private PeriodControllerPanelListener listener;

	private Home home;

	private JLabel currentPeriodLbl;

	public PeriodControllerPanel(Home home) {
		this.home = home;
		new PeriodControllerPanelController(this, home);
		setUp();
		update();
	}

	private void setUp() {
		JLabel currentPeriodTextLbl = new JLabel("Current Period: ");
		currentPeriodTextLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		add(currentPeriodTextLbl);

		currentPeriodLbl = new JLabel();
		currentPeriodLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		add(currentPeriodLbl);

		JButton nextPeriodButton = new JButton("Next period");
		nextPeriodButton.setFont(new Font("Raleway", Font.BOLD, 14));
		nextPeriodButton.setBackground(new Color(0x2dce98));
		nextPeriodButton.setForeground(Color.white);
		nextPeriodButton.setUI(new StyledButtonUI());
		nextPeriodButton.addActionListener((e) -> {
			listener.onNextPeriodButtonClicked();
		});
		add(nextPeriodButton);
	}

	public void update() {
		if (home.getCurrentPeriod() >= Home.START_PERIOD) {
			currentPeriodLbl.setText(String.valueOf(home.getCurrentPeriod()));
		} else {
			currentPeriodLbl.setText(String.valueOf("Not started"));
		}

		updateUI();
	}

	public void setPeriodControllerPanelListener(PeriodControllerPanelListener listener) {
		this.listener = listener;
	}

	public interface PeriodControllerPanelListener {
		void onNextPeriodButtonClicked();
	}
}
