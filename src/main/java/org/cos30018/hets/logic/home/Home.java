package org.cos30018.hets.logic.home;

import java.util.List;

import jade.core.AID;

public interface Home {

	List<AID> getAppliances();
	List<AID> getRetailers();
	void setIntervalPeriod(long period);
	long getIntervalPeriod();
	void setForecastPeriodCount(int forecastPeriodCount);
	int getForecastPeriodCount();
	void setTotalUsageForecast(double totalUsageForecast);
	double getTotalUsageForecast();
	void addListener(HomeListener listener);
	
	public interface HomeListener {
		void onTotalUsageForecastUpdated(double totalUsageForecast);
		void onApplianceAdded(AID applianceAID);
		void onApplianceRemoved(AID applianceAID);
		void onRetailerAdded(AID retailerAID);
		void onRetailerRemoved(AID retailerAID);
	}
	
}
