package org.cos30018.hets.ui.home.dashboard;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledButtonUI;
import org.cos30018.hets.ui.home.HomePanel;

public class PeriodControllerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6425404148702431224L;

	private PeriodControllerPanelListener listener;

	private Home home;

	private JLabel currentPeriodLbl;
	private HomePanel homePanel;

	public PeriodControllerPanel(Home home, HomePanel homePanel) {
		this.home = home;
		this.homePanel = homePanel;
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

		JButton resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Raleway", Font.BOLD, 14));
		resetButton.setBackground(new Color(0x2dce98));
		resetButton.setForeground(Color.white);
		resetButton.setUI(new StyledButtonUI());
		resetButton.addActionListener((e) -> {
			listener.onResetButtonClicked();
		});
		add(resetButton);
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

	public HomePanel getHomePanel() {
		return homePanel;
	}

	public interface PeriodControllerPanelListener {
		void onNextPeriodButtonClicked();

		void onResetButtonClicked();
	}
}
