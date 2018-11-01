package org.cos30018.hets.logic.home;

import java.util.List;
import java.util.Map;

import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.strategy.Strategy;

import jade.core.AID;

public interface Home {
	public static final int START_PERIOD = 0;

	void reset();

	void setPeriod(int period);

	void nextPeriod();

	int getCurrentPeriod();

	int getNextPeriod();

	List<AID> getAppliances();

	List<AID> getRetailers();

	void setForecastPeriodCount(int forecastPeriodCount);

	int getForecastPeriodCount();

	public void setTotalUsageForecast(int period, double totalUsageForecast);

	double getTotalUsageForecast(int period);

	Map<Integer, Double> getTotalUsageForecasts();

	void setActualTotalUsage(int period, double lastActualTotalUsage);

	Map<Integer, Double> getActualTotalUsages();

	double getActualTotalUsage(int period);

	void setNegotiatedOffer(int period, Offer offer);

	Map<Integer, Offer> getNegotiatedOffers();

	Strategy getBuyingStrategy();

	void setBuyingStrategy(Strategy strategy);

	Strategy getSellingStrategy();

	void setSellingStrategy(Strategy strategy);

	double getExcessPrice();

	void setExcessPrice(double price);

	void addListener(HomeListener listener);

	public interface HomeListener {
		void onNewPeriod();

		void onTotalUsageForecastUpdated(int period, double totalUsageForecast);

		void onActualTotalUsageUpdated(int period, double lastActualTotalUsage);

		void onNewNegotiatedOffer(int period, Offer offer);

		void onApplianceAdded(AID applianceAID);

		void onApplianceRemoved(AID applianceAID);

		void onRetailerAdded(AID retailerAID);

		void onRetailerRemoved(AID retailerAID);
	}

}
