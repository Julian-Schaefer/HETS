package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public class TimeDependentStrategy extends Strategy {

	private int deadline;
	private double minValue;
	private double maxValue;
	private double beta;
	private boolean increasing;

	public TimeDependentStrategy(int deadline, double reservationValue, double beta, boolean increasing) {
		this.deadline = deadline;
		this.minValue = reservationValue;
		this.maxValue = initialValue;
		this.beta = beta;
		this.increasing = increasing;
	}

	@Override
	public Offer getCounterOffer(Offer incomingOffer) {
		Offer counterOffer = incomingOffer.createCounterOffer();
		counterOffer.setPrice(calculateNewValue());
		round++;
		return counterOffer;
	}

	private double calculateNewValue() {
		double kj = 0.0;
		double alpha = kj + (1 - kj) * Math.pow((Double.valueOf(round) / Double.valueOf(deadline)), (1.0 / beta));

		if (increasing) {
			return minValue + alpha * (maxValue - minValue);
		} else {
			return minValue + (1 - alpha) * (maxValue - minValue);
		}
	}

	@Override
	public void reset(double initialValue) {
		super.reset(initialValue);
		this.maxValue = initialValue;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_TIME_DEPENDENT;
	}

}
