package org.cos30018.hets.ui.home.dashboard;

import java.awt.*;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledJPanelUI;

public class ApplianceInformationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6902463155733376886L;

	private Home home;

	private JLabel nextForecastedTotalUsageLbl;

	private JLabel forecastedTotalUsageLbl;
	private JLabel actualTotalUsageLbl;
	private JLabel differenceUsageLbl;

	public ApplianceInformationPanel(Home home) {
		this.home = home;
		setLayout(new GridLayout(2, 1));
		setUp();
		update();
	}

	private void setUp() {
		add(getNextPeriodPanel());
		add(getCurrentPeriodPanel());
	}

	private JPanel getNextPeriodPanel() {
		JPanel nextPeriod = new JPanel(new BorderLayout());
		JLabel nextPeriodLbl = new JLabel("Next period:");
		nextPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		nextPeriod.add(nextPeriodLbl, BorderLayout.NORTH);

		JPanel nextPeriodGrid = new JPanel(new GridLayout(1, 2));
		//change
		//nextPeriodGrid.setBackground(Color.WHITE);
		JLabel nextForecastedTotalUsageTextLbl = new JLabel("Next Forecasted total usage: ");
		addCenteredLabel(nextPeriodGrid, nextForecastedTotalUsageTextLbl);

		nextForecastedTotalUsageLbl = new JLabel();
		addCenteredLabel(nextPeriodGrid, nextForecastedTotalUsageLbl);

		nextPeriod.add(nextPeriodGrid, BorderLayout.CENTER);

		return nextPeriod;
	}

	private JPanel getCurrentPeriodPanel() {
		JPanel currentPeriod = new JPanel(new BorderLayout());
		//change
		currentPeriod.setBackground(Color.WHITE);
		JLabel currentPeriodLbl = new JLabel("Current period:");
		currentPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		currentPeriod.add(currentPeriodLbl, BorderLayout.NORTH);

		JPanel currentPeriodGrid = new JPanel(new GridLayout(3, 2));
		//change
		currentPeriodGrid.setBackground(Color.WHITE);


		JLabel forecastedTotalUsageTextLbl = new JLabel("Forecasted total usage: ");
		addCenteredLabel(currentPeriodGrid, forecastedTotalUsageTextLbl);

		forecastedTotalUsageLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, forecastedTotalUsageLbl);

		JLabel actualTotalUsageTextLbl = new JLabel("Actual total usage: ");
		addCenteredLabel(currentPeriodGrid, actualTotalUsageTextLbl);

		actualTotalUsageLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, actualTotalUsageLbl);

		JLabel differenceUsageTextLbl = new JLabel("Difference usage: ");
		addCenteredLabel(currentPeriodGrid, differenceUsageTextLbl);

		differenceUsageLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, differenceUsageLbl);

		currentPeriod.add(currentPeriodGrid, BorderLayout.CENTER);

		return currentPeriod;
	}


	private void addCenteredLabel(JPanel panel, JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
	}

	public void update() {
		nextForecastedTotalUsageLbl.setText(String.valueOf(home.getTotalUsageForecast(home.getNextPeriod())));

		Map<Integer, Double> usageForecasts = home.getTotalUsageForecasts();
		if (usageForecasts.containsKey(home.getCurrentPeriod())) {
			double lastForecast = usageForecasts.get(home.getCurrentPeriod());
			forecastedTotalUsageLbl.setText(String.valueOf(lastForecast));

			double actualUsage = home.getActualTotalUsage(home.getCurrentPeriod());
			actualTotalUsageLbl.setText(String.valueOf(actualUsage));

			differenceUsageLbl.setText(String.valueOf(lastForecast - actualUsage));
		}
	}

}
