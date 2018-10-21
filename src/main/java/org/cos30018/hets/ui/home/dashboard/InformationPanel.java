package org.cos30018.hets.ui.home.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;

public class InformationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6902463155733376886L;

	private Home home;

	private JLabel forecastedTotalUsageLbl;
	private JLabel lastActualTotalUsageLbl;
	private JLabel registeredAppliancesLbl;
	private JLabel registeredRetailersLbl;

	public InformationPanel(Home home) {
		this.home = home;
		setLayout(new GridBagLayout());
		setUp();
		update();
	}

	private void setUp() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		JLabel forecastedTotalUsageTextLbl = new JLabel("Forecasted total usage: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(forecastedTotalUsageTextLbl, gbc);

		forecastedTotalUsageLbl = new JLabel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(forecastedTotalUsageLbl, gbc);

		JLabel lastActualTotalUsageTextLbl = new JLabel("Last actual total usage: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(lastActualTotalUsageTextLbl, gbc);

		lastActualTotalUsageLbl = new JLabel();
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(lastActualTotalUsageLbl, gbc);

		JLabel registeredAppliancesTextLbl = new JLabel("Registered Appliances: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(registeredAppliancesTextLbl, gbc);

		registeredAppliancesLbl = new JLabel();
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(registeredAppliancesLbl, gbc);

		JLabel registeredRetailersTextLbl = new JLabel("Registered Retailers: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(registeredRetailersTextLbl, gbc);

		registeredRetailersLbl = new JLabel();
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(registeredRetailersLbl, gbc);
	}

	public void update() {
		forecastedTotalUsageLbl.setText(String.valueOf(home.getTotalUsageForecast()));
		lastActualTotalUsageLbl.setText(String.valueOf(home.getLastActualTotalUsage()));
		registeredAppliancesLbl.setText(String.valueOf(home.getAppliances().size()));
		registeredRetailersLbl.setText(String.valueOf(home.getRetailers().size()));
	}

}
