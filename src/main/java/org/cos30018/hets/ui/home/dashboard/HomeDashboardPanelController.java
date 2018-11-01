package org.cos30018.hets.ui.home.dashboard;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.ui.custom.graph.ForecastAndActualGraph;
import org.cos30018.hets.ui.custom.graph.NegotiatedAndActualPriceGraph;
import org.cos30018.hets.ui.custom.graph.NegotiatedPriceGraph;

import jade.core.AID;

public class HomeDashboardPanelController implements HomeListener {

	private HomeDashboardPanel homeDashboardPanel;
	private Home home;

	public HomeDashboardPanelController(HomeDashboardPanel homeDashboardPanel, Home home) {
		this.homeDashboardPanel = homeDashboardPanel;
		this.home = home;
		this.home.addListener(this);
	}

	@Override
	public void onTotalUsageForecastUpdated(int period, double totalUsageForecast) {
		ForecastAndActualGraph forecastAndActualGraph = homeDashboardPanel.getForecastAndActualGraph();
		forecastAndActualGraph.addForecastValue(period, totalUsageForecast);

		homeDashboardPanel.update();
	}

	@Override
	public void onActualTotalUsageUpdated(int period, double lastActualTotalUsage) {
		ForecastAndActualGraph forecastAndActualGraph = homeDashboardPanel.getForecastAndActualGraph();
		forecastAndActualGraph.addActualValue(period, lastActualTotalUsage);

		homeDashboardPanel.update();
	}

	@Override
	public void onNewNegotiatedOffer(int period, Offer offer) {
		NegotiatedPriceGraph negotiatedPriceGraph = homeDashboardPanel.getNegotiatedPriceGraph();
		negotiatedPriceGraph.addNegotiatedPrice(period, offer.getPrice());

		NegotiatedAndActualPriceGraph negotiatedAndActualPriceGraph = homeDashboardPanel
				.getNegotiatedAndActualPriceGraph();

		if (home.getNegotiatedOffers().containsKey(home.getNextPeriod())) {
			Offer nextNegotiatedOffer = home.getNegotiatedOffers().get(home.getNextPeriod());
			negotiatedAndActualPriceGraph.addForecastValue(home.getNextPeriod(),
					home.getTotalUsageForecast(home.getNextPeriod()) * nextNegotiatedOffer.getPrice());
		}

		if (home.getNegotiatedOffers().containsKey(home.getCurrentPeriod())) {
			Offer currentNegotiatedOffer = home.getNegotiatedOffers().get(home.getCurrentPeriod());
			double forecastedUsage = home.getTotalUsageForecast(home.getCurrentPeriod());
			double actualUsage = home.getActualTotalUsage(home.getCurrentPeriod());

			double forecastedPrice = forecastedUsage * currentNegotiatedOffer.getPrice();
			double actualPrice = actualUsage * currentNegotiatedOffer.getPrice();

			if (forecastedUsage < actualUsage) {
				double difference = actualUsage - forecastedUsage;
				double differencePrice = difference * currentNegotiatedOffer.getExcessPrice();
				negotiatedAndActualPriceGraph.addActualValue(home.getCurrentPeriod(), actualPrice);
				negotiatedAndActualPriceGraph.addDifferenceValue(home.getCurrentPeriod(), differencePrice);
			} else {
				negotiatedAndActualPriceGraph.addActualValue(home.getCurrentPeriod(), actualPrice);
				negotiatedAndActualPriceGraph.addDifferenceValue(home.getCurrentPeriod(),
						forecastedPrice - actualPrice);
			}
		}

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

	@Override
	public void onNewPeriod() {
	}
}
