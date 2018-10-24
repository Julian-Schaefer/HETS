package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public abstract class Strategy implements Cloneable {

	public static final String STRATEGY_FIXED_PRICE = "Fixed Price";
	public static final String STRATEGY_MODELLING = "Modelling";
	public static final String STRATEGY_TIME_DEPENDENT = "Time-Dependent";

	protected double initialValue;
	protected int round;

	public abstract Offer getCounterOffer(Offer incomingOffer);

	public void reset(double initialValue) {
		this.initialValue = initialValue;
		this.round = 0;
	}

	public abstract String getName();

	public int getRound() {
		return round;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
