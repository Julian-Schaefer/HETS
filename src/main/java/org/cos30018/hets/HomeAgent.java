package org.cos30018.hets;

import java.util.List;

public interface HomeAgent {

	void addApplianceAgent(ApplianceAgent agent);
	void removeApplianceAgent(ApplianceAgent agent);
	void addRetailerAgent(RetailerAgent agent);
	void removeRetailerAgent(RetailerAgent agent);
	
	//List<Estimation> getEstimations();
	//List<Offer> getOffers();
	
}
