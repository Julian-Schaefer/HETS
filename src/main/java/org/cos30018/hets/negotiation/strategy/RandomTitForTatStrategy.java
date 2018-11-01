package org.cos30018.hets.negotiation.strategy;

public class RandomTitForTatStrategy extends Strategy {

	private int deadline;
	private double factor;

	private Double lastIncomingValue;
	private Double lastOutgoingValue;

	public RandomTitForTatStrategy(int deadline, double initialValue, double reservationValue, double factor) {
		super(initialValue, reservationValue);
		this.deadline = deadline;
		this.reservationValue = reservationValue;
		this.factor = factor;
	}

	@Override
	public double getNewValue(double incomingValue) throws DeadlineExceededException {
		if (round == deadline) {
			throw new DeadlineExceededException();
		}

		if (lastIncomingValue == null) {
			lastOutgoingValue = initialValue;
		} else {
			double difference = incomingValue - lastIncomingValue;
			difference *= difference < 1 ? -1 : 1;

			double randomness = (Math.random() * difference) * factor;

			if (initialValue > reservationValue) {
				lastOutgoingValue -= difference + randomness;
			} else {
				lastOutgoingValue += difference + randomness;
			}
		}

		if (lastOutgoingValue < reservationValue) {
			throw new DeadlineExceededException();
		}

		round++;
		lastIncomingValue = incomingValue;
		return lastOutgoingValue;
	}

	public int getDeadline() {
		return deadline;
	}

	public double getFactor() {
		return factor;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_RANDOM_TIT_FOR_TAT;
	}
}
