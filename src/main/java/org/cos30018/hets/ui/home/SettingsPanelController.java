package org.cos30018.hets.ui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;
import org.cos30018.hets.negotiation.Offer;

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
		long interval;
		int periods;

		try {
			interval = Long.valueOf(settingsPanel.getPeriodIntervalTextField().getText());
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			periods = Integer.valueOf(settingsPanel.getForecastPeriodsTextField().getText());
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		home.setIntervalPeriod(interval * 1000);
		home.setForecastPeriodCount(periods);
		settingsPanel.update();
	}

	@Override
	public void onTotalUsageForecastUpdated(int period, double totalUsageForecast) {
	}

	@Override
	public void onActualTotalUsageUpdated(int period, double lastActualTotalUsage) {
	}

	@Override
	public void onNewNegotiatedOffer(int period, Offer offer) {
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
