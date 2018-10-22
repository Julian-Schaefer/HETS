package org.cos30018.hets.logic.home;

import java.util.List;
import java.util.Map;

import org.cos30018.hets.negotiation.Offer;

import jade.core.AID;

public interface Home {
	public static final int START_PERIOD = 0;

	void setPeriod(int period);

	void nextPeriod();

	int getCurrentPeriod();

	int getNextPeriod();

	List<AID> getAppliances();

	List<AID> getRetailers();

	void setIntervalPeriod(long period);

	long getIntervalPeriod();

	void setForecastPeriodCount(int forecastPeriodCount);

	int getForecastPeriodCount();

	public void setTotalUsageForecast(int period, double totalUsageForecast);

	double getTotalUsageForecast();

	Map<Integer, Double> getTotalUsageForecasts();

	void setActualTotalUsage(int period, double lastActualTotalUsage);

	Map<Integer, Double> getActualTotalUsages();

	double getLastActualTotalUsage();

	void addListener(HomeListener listener);

	void setNegotiatedOffer(int period, Offer offer);

	Map<Integer, Offer> getNegotiatedOffers();

	public interface HomeListener {
		void onTotalUsageForecastUpdated(int period, double totalUsageForecast);

		void onActualTotalUsageUpdated(int period, double lastActualTotalUsage);

		void onNewNegotiatedOffer(int period, Offer offer);

		void onApplianceAdded(AID applianceAID);

		void onApplianceRemoved(AID applianceAID);

		void onRetailerAdded(AID retailerAID);

		void onRetailerRemoved(AID retailerAID);
	}

}
