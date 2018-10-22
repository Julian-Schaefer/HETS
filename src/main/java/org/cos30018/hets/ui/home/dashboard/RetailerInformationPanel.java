package org.cos30018.hets.ui.home.dashboard;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;

public class RetailerInformationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6902463155733376886L;

	private Home home;

	private JLabel forecastedTotalUsageLbl;
	private JLabel lastActualTotalUsageLbl;
	private JLabel registeredAppliancesLbl;
	private JLabel registeredRetailersLbl;

	public RetailerInformationPanel(Home home) {
		this.home = home;
		setLayout(new GridLayout(4, 2));
		setUp();
		update();
	}

	private void setUp() {
		JLabel forecastedTotalUsageTextLbl = new JLabel("Forecasted total usage: ");
		addCenteredLabel(forecastedTotalUsageTextLbl);

		forecastedTotalUsageLbl = new JLabel();
		addCenteredLabel(forecastedTotalUsageLbl);

		JLabel lastActualTotalUsageTextLbl = new JLabel("Last actual total usage: ");
		addCenteredLabel(lastActualTotalUsageTextLbl);

		lastActualTotalUsageLbl = new JLabel();
		addCenteredLabel(lastActualTotalUsageLbl);

		JLabel registeredAppliancesTextLbl = new JLabel("Registered Appliances: ");
		addCenteredLabel(registeredAppliancesTextLbl);

		registeredAppliancesLbl = new JLabel();
		addCenteredLabel(registeredAppliancesLbl);

		JLabel registeredRetailersTextLbl = new JLabel("Registered Retailers: ");
		addCenteredLabel(registeredRetailersTextLbl);

		registeredRetailersLbl = new JLabel();
		addCenteredLabel(registeredRetailersLbl);
	}

	private void addCenteredLabel(JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label);
	}

	public void update() {
		forecastedTotalUsageLbl.setText(String.valueOf(home.getTotalUsageForecast()));
		lastActualTotalUsageLbl.setText(String.valueOf(home.getLastActualTotalUsage()));
		registeredAppliancesLbl.setText(String.valueOf(home.getAppliances().size()));
		registeredRetailersLbl.setText(String.valueOf(home.getRetailers().size()));
	}

}