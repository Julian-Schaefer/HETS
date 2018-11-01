package org.cos30018.hets.ui.home;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;
import org.cos30018.hets.negotiation.Offer;

import jade.core.AID;

public class SettingsPanelController implements HomeListener {

	private SettingsPanel settingsPanel;
	private Home home;

	public SettingsPanelController(SettingsPanel settingsPanel, Home home) {
		this.settingsPanel = settingsPanel;
		this.home = home;
		this.home.addListener(this);
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

	@Override
	public void onNewPeriod() {
	}
}
