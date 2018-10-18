package org.cos30018.hets.logic.retailer;

import java.util.List;

public interface Retailer {

	public enum NegotiationStrategy {
		FIXED, COMPLEX
	}

	public enum PricingStrategy {
		HIGH, LOW, EFFICIENT
	}

	void setNegotiationStrategy(NegotiationStrategy negotiationStrategy);

	NegotiationStrategy getNegotiationStrategy();

	void setPricingStrategy(PricingStrategy pricingStrategy);

	PricingStrategy getPricingStrategy();

	void setVolumeCharge(double volumeCharge);

	double getVolumeCharge();

	void setFeedInRate(double feedInRate);

	double getFeedInRate();

	List<String> getNegotiationMessages();

	void addRetailerListener(RetailerListener listener);

	public interface RetailerListener {
		void onNegotiationMessageAdded(String message);
	}

}
