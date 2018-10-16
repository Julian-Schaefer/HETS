package org.cos30018.hets.logic.home;

import java.util.List;

import jade.core.AID;

public interface Home {

	void setPeriod(int period);

	void nextPeriod();

	int getPeriod();

	List<AID> getAppliances();

	List<AID> getRetailers();

	void setIntervalPeriod(long period);

	long getIntervalPeriod();

	void setForecastPeriodCount(int forecastPeriodCount);

	int getForecastPeriodCount();

	public void setTotalUsageForecast(double totalUsageForecast);

	double getTotalUsageForecast();

	void setLastActualTotalUsage(double lastActualTotalUsage);

	double getLastActualTotalUsage();

	void addListener(HomeListener listener);

	public interface HomeListener {
		void onTotalUsageForecastUpdated(double totalUsageForecast);

		void onLastActualTotalUsageUpdated(double lastActualTotalUsage);

		void onApplianceAdded(AID applianceAID);

		void onApplianceRemoved(AID applianceAID);

		void onRetailerAdded(AID retailerAID);

		void onRetailerRemoved(AID retailerAID);
	}

}
