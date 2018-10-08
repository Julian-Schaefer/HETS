package org.cos30018.hets.logic.home;

import java.util.List;

import jade.core.AID;

public interface Home {

	List<AID> getAppliances();
	void setCheckInterval();
	void setForecastPeriodCount();
	void setListener(HomeListener listener);
	
	public interface HomeListener {
		void onApplianceAdded(AID applianceAID);
		void onApplianceRemoved(AID applianceAID);
		void onRetailerAdded(AID retailerAID);
		void onRetailerRemoved(AID retailerAID);
	}
	
}
