package org.cos30018.hets.logic.retailer;

import java.util.List;

import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.tariff.Tariff;

public interface Retailer {

	public enum NegotiationStrategy {
		FIXED, COMPLEX
	}

	public enum PricingStrategy {
		HIGH, LOW, EFFICIENT
	}

	Tariff getTariff();

	Strategy getStrategy();

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
