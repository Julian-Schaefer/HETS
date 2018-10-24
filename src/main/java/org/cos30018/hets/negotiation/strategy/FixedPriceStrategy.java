package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public class FixedPriceStrategy extends Strategy {

	@Override
	public Offer getCounterOffer(Offer incomingOffer) {
		Offer counterOffer = incomingOffer.createCounterOffer();
		counterOffer.setPrice(initialPrice);
		round++;
		return counterOffer;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_FIXED_PRICE;
	}

	@Override
	public void reset(double initialPrice) {
		super.reset(initialPrice);
	}
}
