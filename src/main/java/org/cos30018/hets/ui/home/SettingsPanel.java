package org.cos30018.hets.ui.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledButtonUI;
import org.cos30018.hets.ui.custom.button.StyledRoundButtonUI;

import net.miginfocom.swing.MigLayout;

public class SettingsPanel extends JPanel {

	public static final String RESERVATION_VALUE_CHANGE = "reservation_value";
	public static final String PERIOD_CHANGE = "period_change";
	public static final String FORECAST_PERIODS_CHANGE = "forecast_periods_change";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1449215169896590692L;

	private SettingsPanelListener listener;
	private SettingsPanelController controller;

	private JButton homeButton;
	private JLabel registeredAppliancesLabel;
	private JLabel registeredRetailersLabel;
	private JTextField reservationValueTextField;
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
		setLayout(new MigLayout("insets 20 20 20 20"));
		// setBackground(Color.white);

		JLabel titleSettings = new JLabel("Settings");
		titleSettings.setFont(new Font("Raleway", Font.BOLD, 40));

		JLabel subTitleGeneral = new JLabel("General");
		subTitleGeneral.setFont(new Font("Raleway", Font.BOLD, 24));

		homeButton = new JButton();
		homeButton.setIcon(new ImageIcon(getClass().getResource("/images/home_outline_2x_18dp.png")));
		homeButton.setBackground(new Color(0x2dce98));
		homeButton.setForeground(Color.white);
		homeButton.setUI(new StyledRoundButtonUI());
		homeButton.addActionListener(homeButtonActionListener);

		add(titleSettings);
		add(homeButton, "wrap");

		add(subTitleGeneral, "wrap 20");

		JPanel content = new JPanel(new MigLayout("center"));
		content.setPreferredSize(new Dimension(1280, 800));
		content.setBackground(Color.white);

		JLabel subTitle = new JLabel("General");
		subTitle.setFont(new Font("Raleway", Font.PLAIN, 20));

		JLabel reservationValueLbl = new JLabel("Reservation Value (Cent/kwH)");
		reservationValueLbl.setFont(new Font("Raleway", Font.PLAIN, 16));

		JLabel lblTimeInterval = new JLabel("Current Time Interval (seconds)");
		lblTimeInterval.setFont(new Font("Raleway", Font.PLAIN, 16));

		JLabel lblForecast = new JLabel("Forecast for the next n periods");
		lblForecast.setFont(new Font("Raleway", Font.PLAIN, 16));

		JLabel lblRegisterApps = new JLabel("Currently Registered Appliances");
		lblRegisterApps.setFont(new Font("Raleway", Font.PLAIN, 16));

		JLabel lblRegisterRetailers = new JLabel("Currently Registered Retailers");
		lblRegisterRetailers.setFont(new Font("Raleway", Font.PLAIN, 16));

		reservationValueTextField = new JTextField(8);
		periodIntervalTextField = new JTextField(8);
		forecastPeriodsTextField = new JTextField(8);

		JButton reservationValueChangeButton = new JButton("Change");
		JButton periodChangeButton = new JButton("Change");
		JButton forecastChangeButton = new JButton("Change");

		reservationValueChangeButton.setBackground(new Color(0x2dce98));
		reservationValueChangeButton.setFont(new Font("Raleway", Font.BOLD, 14));
		reservationValueChangeButton.setForeground(Color.white);
		reservationValueChangeButton.setUI(new StyledButtonUI());
		reservationValueChangeButton.setActionCommand(RESERVATION_VALUE_CHANGE);
		reservationValueChangeButton.addActionListener(controller);

		periodChangeButton.setBackground(new Color(0x2dce98));
		periodChangeButton.setFont(new Font("Raleway", Font.BOLD, 14));
		periodChangeButton.setForeground(Color.white);
		periodChangeButton.setUI(new StyledButtonUI());
		periodChangeButton.setActionCommand(PERIOD_CHANGE);
		periodChangeButton.addActionListener(controller);

		forecastChangeButton.setBackground(new Color(0x2dce98));
		forecastChangeButton.setFont(new Font("Raleway", Font.BOLD, 14));
		forecastChangeButton.setForeground(Color.white);
		forecastChangeButton.setUI(new StyledButtonUI());
		forecastChangeButton.setActionCommand(FORECAST_PERIODS_CHANGE);
		forecastChangeButton.addActionListener(controller);

		registeredAppliancesLabel = new JLabel();
		registeredRetailersLabel = new JLabel();

		// content.add(subTitle, "wrap");

		content.add(reservationValueLbl, "span 1");
		content.add(reservationValueTextField, "span 1");
		content.add(reservationValueChangeButton, "span 3, wrap 5");

		content.add(lblTimeInterval, "span 1");
		content.add(periodIntervalTextField, "span 1");
		content.add(periodChangeButton, "span 3, wrap 5");

		content.add(lblForecast);
		content.add(forecastPeriodsTextField);
		content.add(forecastChangeButton, "wrap 5");

		content.add(lblRegisterApps);
		content.add(registeredAppliancesLabel, "wrap 5");

		content.add(lblRegisterRetailers);
		content.add(registeredRetailersLabel, "wrap");

		add(content);
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

	public JTextField getReservationValueTextField() {
		return reservationValueTextField;
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
