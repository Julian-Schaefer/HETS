package org.cos30018.hets.negotiation.strategy;

public class ResourceDependentStrategy extends Strategy {

	public ResourceDependentStrategy(double reservationValue) {
		super(reservationValue);
	}

	@Override
	public double getNewValue() throws DeadlineExceededException {
		return 0;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_RESOURCE_DEPENDENT;
	}

}
