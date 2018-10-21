package org.cos30018.hets.ui.home.dashboard;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;
import org.cos30018.hets.ui.custom.ForecastAndActualGraph;
import org.cos30018.hets.ui.custom.NegotiatedPriceGraph;

import jade.core.AID;

public class HomeDashboardPanelController implements HomeListener {

	HomeDashboardPanel homeDashboardPanel;

	public HomeDashboardPanelController(HomeDashboardPanel homeDashboardPanel, Home home) {
		this.homeDashboardPanel = homeDashboardPanel;
		home.addListener(this);
	}

	@Override
	public void onTotalUsageForecastUpdated(double totalUsageForecast) {
		ForecastAndActualGraph forecastAndActualGraph = homeDashboardPanel.getForecastAndActualGraph();
		forecastAndActualGraph.addForecastValue(totalUsageForecast);

		homeDashboardPanel.update();
	}

	@Override
	public void onLastActualTotalUsageUpdated(double lastActualTotalUsage) {
		ForecastAndActualGraph forecastAndActualGraph = homeDashboardPanel.getForecastAndActualGraph();
		forecastAndActualGraph.addActualValue(lastActualTotalUsage);
		forecastAndActualGraph.nextPeriod();

		homeDashboardPanel.update();
	}

	@Override
	public void onNegotiatedPriceUpdate(double negotiatedPrice) {
		NegotiatedPriceGraph negotiatedPriceGraph = homeDashboardPanel.getNegotiatedPriceGraph();
		negotiatedPriceGraph.addNegotiatedPrice(negotiatedPrice);
		negotiatedPriceGraph.nextPeriod();

		homeDashboardPanel.update();
	}

	@Override
	public void onApplianceAdded(AID applianceAID) {
		homeDashboardPanel.update();
	}

	@Override
	public void onApplianceRemoved(AID applianceAID) {
		homeDashboardPanel.update();
	}

	@Override
	public void onRetailerAdded(AID retailerAID) {
		homeDashboardPanel.update();
	}

	@Override
	public void onRetailerRemoved(AID retailerAID) {
		homeDashboardPanel.update();
	}
}
