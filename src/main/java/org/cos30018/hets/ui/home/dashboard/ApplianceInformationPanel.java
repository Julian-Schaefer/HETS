package org.cos30018.hets.ui.home.dashboard;

import java.awt.*;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledInfoCardUI;
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
		JPanel nextPeriodBase = new JPanel(new BorderLayout());
		nextPeriodBase.setBackground(Color.WHITE);
		nextPeriodBase.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel nextPeriod = new JPanel(new BorderLayout());
		nextPeriod.setUI(new StyledInfoCardUI());
		nextPeriod.setBorder(new EmptyBorder(20, 20, 20, 20));
		nextPeriod.setBackground(Color.WHITE);

		JLabel nextPeriodLbl = new JLabel("Next period:");
		nextPeriodLbl.setForeground((new Color(0x2dce98)));
		nextPeriodLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		nextPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		nextPeriod.add(nextPeriodLbl, BorderLayout.NORTH);

		JPanel nextPeriodGrid = new JPanel(new GridLayout(3, 1));
		//change
		nextPeriodGrid.setBackground(Color.WHITE);
		JLabel nextForecastedTotalUsageTextLbl = new JLabel("Next Forecasted total usage: ");
		nextForecastedTotalUsageTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(nextPeriodGrid, nextForecastedTotalUsageTextLbl);

		nextForecastedTotalUsageLbl = new JLabel();
		nextForecastedTotalUsageLbl.setFont(new Font("Raleway", Font.BOLD, 12));
		addCenteredLabel(nextPeriodGrid, nextForecastedTotalUsageLbl);

		nextPeriod.add(nextPeriodGrid, BorderLayout.CENTER);
		nextPeriodBase.add(nextPeriod);

		return nextPeriodBase;
	}

	private JPanel getCurrentPeriodPanel() {
		JPanel currentPeriodBase = new JPanel(new BorderLayout());
		currentPeriodBase.setBackground(Color.WHITE);
		currentPeriodBase.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel currentPeriod = new JPanel(new BorderLayout());
		currentPeriod.setUI(new StyledInfoCardUI());
		//change
		currentPeriod.setBackground(Color.WHITE);
		currentPeriod.setBorder(new EmptyBorder(20, 20, 20, 20));
		JLabel currentPeriodLbl = new JLabel("Current period:");
		currentPeriodLbl.setForeground((new Color(0x2dce98)));
		currentPeriodLbl.setFont(new Font("Raleway", Font.BOLD, 14));

		currentPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		currentPeriod.add(currentPeriodLbl, BorderLayout.NORTH);

		JPanel currentPeriodGrid = new JPanel(new GridLayout(6, 1));
		//change
		currentPeriodGrid.setBackground(Color.WHITE);


		JLabel forecastedTotalUsageTextLbl = new JLabel("Forecasted total usage: ");
		forecastedTotalUsageTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, forecastedTotalUsageTextLbl);

		forecastedTotalUsageLbl = new JLabel("-");
		forecastedTotalUsageLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, forecastedTotalUsageLbl);

		JLabel actualTotalUsageTextLbl = new JLabel("Actual total usage: ");
		actualTotalUsageTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, actualTotalUsageTextLbl);

		actualTotalUsageLbl = new JLabel("-");
		actualTotalUsageLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, actualTotalUsageLbl);

		JLabel differenceUsageTextLbl = new JLabel("Difference usage: ");
		differenceUsageTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, differenceUsageTextLbl);

		differenceUsageLbl = new JLabel("-");
		differenceUsageLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, differenceUsageLbl);

		currentPeriod.add(currentPeriodGrid, BorderLayout.CENTER);
		currentPeriodBase.add(currentPeriod);

		return currentPeriodBase;
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
