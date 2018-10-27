package org.cos30018.hets.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.StrategyConfigurationPanel;
import org.cos30018.hets.ui.custom.button.StyledButtonUI;
import org.cos30018.hets.ui.custom.button.StyledRoundButtonUI;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1449215169896590692L;

	private SettingsPanelListener listener;
	private SettingsPanelController controller;

	private JButton homeButton;
	private JLabel registeredAppliancesLabel;
	private JLabel registeredRetailersLabel;
	private StrategyConfigurationPanel strategyConfigurationPanel;
	private JTextField periodIntervalTextField;
	private JTextField forecastPeriodsTextField;

	private Home home;

	public SettingsPanel(Home home) {
		this.home = home;
		this.controller = new SettingsPanelController(this, home);
		setUp();
		update();
	}

	private void setUp() {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.white);

		JLabel titleSettings = new JLabel("Settings");
		titleSettings.setFont(new Font("Raleway", Font.BOLD, 40));
		topPanel.add(titleSettings, BorderLayout.WEST);

		homeButton = new JButton();
		homeButton.setIcon(new ImageIcon(getClass().getResource("/images/home_outline_2x_18dp.png")));
		homeButton.setBackground(new Color(0x2dce98));
		homeButton.setForeground(Color.white);
		homeButton.setUI(new StyledRoundButtonUI());
		homeButton.addActionListener(homeButtonActionListener);

		topPanel.add(homeButton, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridLayout(5, 2));
		centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		centerPanel.setBackground(Color.white);

		JLabel negotiationStrategyLbl = new JLabel("Negotiation Strategy");
		negotiationStrategyLbl.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(negotiationStrategyLbl));

		JButton negotiationStrategyChangeButton = new JButton("Change Strategy");
		negotiationStrategyChangeButton.addActionListener((e) -> {
			new NegotiationStrategySelectorWindow(home);
		});
		centerPanel.add(addToPanel(negotiationStrategyChangeButton));

		JLabel lblTimeInterval = new JLabel("Current Time Interval (seconds)");
		lblTimeInterval.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(lblTimeInterval));

		periodIntervalTextField = new JTextField(8);
		centerPanel.add(addToPanel(periodIntervalTextField));

		JLabel lblForecast = new JLabel("Forecast for the next n periods");
		lblForecast.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(lblForecast));

		forecastPeriodsTextField = new JTextField(8);
		centerPanel.add(addToPanel(forecastPeriodsTextField));

		JLabel lblRegisterApps = new JLabel("Currently Registered Appliances");
		lblRegisterApps.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(lblRegisterApps));

		registeredAppliancesLabel = new JLabel();
		centerPanel.add(addToPanel(registeredAppliancesLabel));

		JLabel lblRegisterRetailers = new JLabel("Currently Registered Retailers");
		lblRegisterRetailers.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(lblRegisterRetailers));

		registeredRetailersLabel = new JLabel();
		centerPanel.add(addToPanel(registeredRetailersLabel));

		add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.white);
		JButton changeButton = new JButton("Change");
		changeButton.setBackground(new Color(0x2dce98));
		changeButton.setFont(new Font("Raleway", Font.BOLD, 14));
		changeButton.setForeground(Color.white);
		changeButton.setUI(new StyledButtonUI());
		changeButton.addActionListener(controller);
		bottomPanel.add(changeButton);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	private JPanel addToPanel(JComponent component) {
		JPanel panel = new JPanel();
		panel.add(component);
		return panel;
	}

	public void update() {
		registeredAppliancesLabel.setText(String.valueOf(home.getAppliances().size()));
		registeredRetailersLabel.setText(String.valueOf(home.getRetailers().size()));

		String forecastPeriod = String.valueOf(home.getForecastPeriodCount());
		if (!forecastPeriod.equals(forecastPeriodsTextField.getText()) && !forecastPeriodsTextField.hasFocus()) {
			forecastPeriodsTextField.setText(forecastPeriod);
		}

		String periodInterval = String.valueOf(home.getIntervalPeriod() / 1000);
		if (!periodInterval.equals(periodIntervalTextField.getText()) && !periodIntervalTextField.hasFocus()) {
			periodIntervalTextField.setText(periodInterval);
		}
	}

	public JTextField getPeriodIntervalTextField() {
		return periodIntervalTextField;
	}

	public JTextField getForecastPeriodsTextField() {
		return forecastPeriodsTextField;
	}

	public void setSettingsPanelListener(SettingsPanelListener listener) {
		this.listener = listener;
	}

	private ActionListener homeButtonActionListener = (e) -> {
		listener.onHomeButtonClicked();
	};

	public interface SettingsPanelListener {
		void onHomeButtonClicked();
	}
}
