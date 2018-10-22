package org.cos30018.hets.logic.home;

import java.util.List;
import java.util.Map;

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

	void setActualTotalUsage(int period, double lastActualTotalUsage);

	double getLastActualTotalUsage();

	void addListener(HomeListener listener);

	void setNegotiatedPrice(int period, double price);

	Map<Integer, Double> getNegotiatedPrices();

	public interface HomeListener {
		void onTotalUsageForecastUpdated(int period, double totalUsageForecast);

		void onActualTotalUsageUpdated(int period, double lastActualTotalUsage);

		void onNegotiatedPriceUpdate(int period, double negotiatedPrice);

		void onApplianceAdded(AID applianceAID);

		void onApplianceRemoved(AID applianceAID);

		void onRetailerAdded(AID retailerAID);

		void onRetailerRemoved(AID retailerAID);
	}

}
