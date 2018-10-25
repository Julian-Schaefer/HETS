package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public class TimeDependentStrategy extends Strategy {

	private int deadline;
	private double minValue;
	private double maxValue;
	private double beta;

	public TimeDependentStrategy(int deadline, double reservationValue, double beta) {
		this.deadline = deadline;
		this.minValue = initialValue;
		this.maxValue = reservationValue;
		this.beta = beta;
	}

	@Override
	public Offer getCounterOffer(Offer incomingOffer) {
		if (round == deadline + 1) {
			return Offer.refuse();
		}

		Offer counterOffer = incomingOffer.createCounterOffer();
		counterOffer.setPrice(calculateNewValue());
		round++;
		return counterOffer;
	}

	private double calculateNewValue() {
		double kj = 0.0;
		double alpha = kj + (1 - kj) * Math.pow((Double.valueOf(round) / Double.valueOf(deadline)), (1.0 / beta));

		return minValue + alpha * (maxValue - minValue);
	}

	@Override
	public void reset(double initialValue) {
		super.reset(initialValue);

		this.minValue = initialValue;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_TIME_DEPENDENT;
	}

}
