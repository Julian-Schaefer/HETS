package org.cos30018.hets.ui.home.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.CustomLabelJButton;
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
		setLayout(new BorderLayout());
		setUp();
		update();
	}

	private void setUp() {
		JPanel setPeriodPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JPanel textStatusPanel = new JPanel();

		setPeriodPanel.setBackground(Color.WHITE);
		btnPanel.setBackground(Color.WHITE);
		textStatusPanel.setBackground(Color.WHITE);

		JLabel setTimePeriodTextLbl = new JLabel("Time Period (Seconds)");
		setTimePeriodTextLbl.setFont(new Font("Raleway", Font.PLAIN, 14));
		setPeriodPanel.add(setTimePeriodTextLbl);

		JTextField timeIntervalTextField = new JTextField(5);
		timeIntervalTextField.setText(String.valueOf(5));
		timeIntervalTextField.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		timeIntervalTextField.setFont(new Font("Raleway", Font.BOLD, 14));
		setPeriodPanel.add(timeIntervalTextField);

		CustomLabelJButton startSimulationButton = new CustomLabelJButton("Start Simulation", "Raleway", 14,
				new StyledButtonUI());
		startSimulationButton.addActionListener((e) -> {

			try {
				double seconds = Double.parseDouble(timeIntervalTextField.getText());
				listener.onStartSimulationClicked(seconds);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnPanel.add(startSimulationButton);

		CustomLabelJButton pauseSimulationButton = new CustomLabelJButton("Pause", "Raleway", 14, new StyledButtonUI());
		pauseSimulationButton.addActionListener((e) -> {
			listener.onPauseSimulationClicked();
		});
		btnPanel.add(pauseSimulationButton);

		CustomLabelJButton nextPeriodButton = new CustomLabelJButton("Next Period", "Raleway", 14,
				new StyledButtonUI());
		nextPeriodButton.addActionListener((e) -> {
			listener.onNextPeriodButtonClicked();
		});
		btnPanel.add(nextPeriodButton);

		CustomLabelJButton resetPeriodButton = new CustomLabelJButton("Reset", "Raleway", 14, new StyledButtonUI());
		resetPeriodButton.addActionListener((e) -> {
			listener.onResetButtonClicked();
		});
		btnPanel.add(resetPeriodButton);

		add(setPeriodPanel, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
		add(textStatusPanel, BorderLayout.SOUTH);

		JLabel currentPeriodTextLbl = new JLabel("Current Period: ");
		currentPeriodTextLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		textStatusPanel.add(currentPeriodTextLbl);

		currentPeriodLbl = new JLabel();
		currentPeriodLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		textStatusPanel.add(currentPeriodLbl);
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

		void onStartSimulationClicked(double seconds);

		void onPauseSimulationClicked();

		void onResetButtonClicked();
	}
}
