package org.cos30018.hets.ui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;

import jade.core.AID;

public class SettingsPanelController implements HomeListener, ActionListener {

	private SettingsPanel settingsPanel;
	private Home home;

	public SettingsPanelController(SettingsPanel settingsPanel, Home home) {
		this.settingsPanel = settingsPanel;
		this.home = home;
		this.home.addListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(SettingsPanel.PERIOD_CHANGE)) {
			try {
				long interval = Long.valueOf(settingsPanel.getPeriodIntervalTextField().getText());
				home.setIntervalPeriod(interval * 1000);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand().equals(SettingsPanel.FORECAST_PERIODS_CHANGE)) {
			try {
				int periods = Integer.valueOf(settingsPanel.getForecastPeriodsTextField().getText());
				home.setForecastPeriodCount(periods);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		settingsPanel.update();
	}

	@Override
	public void onTotalUsageForecastUpdated(double totalUsageForecast) {
		settingsPanel.update();
	}

	@Override
	public void onLastActualTotalUsageUpdated(double lastActualTotalUsage) {
	}

	@Override
	public void onNegotiatedPriceUpdate(double negotiatedPrice) {
	}

	@Override
	public void onApplianceAdded(AID applianceAID) {
		settingsPanel.update();
	}

	@Override
	public void onApplianceRemoved(AID applianceAID) {
		settingsPanel.update();
	}

	@Override
	public void onRetailerAdded(AID retailerAID) {
		settingsPanel.update();
	}

	@Override
	public void onRetailerRemoved(AID retailerAID) {
		settingsPanel.update();
	}
}
