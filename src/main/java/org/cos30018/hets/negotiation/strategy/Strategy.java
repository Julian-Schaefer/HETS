package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public abstract class Strategy {

	public static final String STRATEGY_FIXED_PRICE = "Fixed Price";
	public static final String STRATEGY_MODELLING = "Modelling";

	protected double initialPrice;

	public abstract Offer getCounterOffer(Offer incomingOffer);

	public void reset(double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public abstract String getName();
}
