package org.cos30018.hets.negotiation.strategy;

public class FixedPriceStrategy extends Strategy {

	public FixedPriceStrategy(double initialValue) {
		super(initialValue, 0);
	}

	@Override
	public double getNewValue() {
		round++;
		return initialValue;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_FIXED_PRICE;
	}

	@Override
	public void reset(double initialPrice) {
		super.reset(initialPrice);
		reservationValue = initialPrice;
	}
}
