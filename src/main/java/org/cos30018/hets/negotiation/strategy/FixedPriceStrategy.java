package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public class FixedPriceStrategy extends Strategy {

	private double price;

	public FixedPriceStrategy(double price) {
		this.price = price;
	}

	@Override
	public Offer getCounterOffer(Offer incomingOffer) {
		Offer counterOffer = incomingOffer.createCounterOffer();
		counterOffer.setPrice(price);
		return counterOffer;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_FIXED_PRICE;
	}
}
