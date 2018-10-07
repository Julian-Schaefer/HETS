package org.cos30018.hets.ui;

import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.retailer.RetailerAgent;

public interface HomeAgentInterface {

	void addAppliance(ApplianceAgent applianceAgent);
	void removeAppliance(ApplianceAgent applianceAgent);
	void addRetailer(RetailerAgent retailerAgent);
	void removeRetailer(RetailerAgent retailerAgent);
	void setCheckInterval();
	void setForecastPeriodCount();
	
	public interface HomeAgentListener {
		void onApplianceAdded(ApplianceAgent appliance);
		void onApplianceRemoved(ApplianceAgent appliance);
		void onRetailerAdded(RetailerAgent retailer);
		void onRetailerRemoved(RetailerAgent retailer);
	}
	
	
}
