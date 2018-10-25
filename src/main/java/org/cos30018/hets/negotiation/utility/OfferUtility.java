package org.cos30018.hets.negotiation.utility;

import org.cos30018.hets.negotiation.Offer;

public class OfferUtility extends CardinalUtility<Offer> {

	private PriceUtility priceUtility;

	public OfferUtility(double minValue, double maxValue, double minEvaluation, double maxEvaluation) {
		priceUtility = new PriceUtility(minValue, maxValue, minEvaluation, maxEvaluation);
	}

	@Override
	public double getUtility(Offer offer) {
		return priceUtility.getUtility(offer.getPrice());
	}

}
