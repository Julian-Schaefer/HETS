package org.cos30018.hets.negotiation.strategy;

public class ResourceDependentStrategy extends Strategy {

	public ResourceDependentStrategy(double initialValue, double reservationValue) {
		super(initialValue, reservationValue);
	}

	@Override
	public double getNewValue(double resources) throws DeadlineExceededException {
		return 0;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_RESOURCE_DEPENDENT;
	}
}
