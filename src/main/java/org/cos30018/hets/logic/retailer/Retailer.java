package org.cos30018.hets.logic.retailer;

import java.util.List;
import java.util.Map;

import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.tariff.Tariff;

public interface Retailer {

	void reset();

	String getLocalName();

	Tariff getTariff();

	Strategy getBuyingStrategy();

	Strategy getSellingStrategy();

	List<String> getNegotiationMessages();

	Map<Integer, Offer> getIncomingOffers();

	Map<Integer, Offer> getOutgoingOffers();

	void addRetailerListener(RetailerListener listener);

	public interface RetailerListener {
		void onNegotiationMessagesUpdated();

		void onReset();
	}

}
