package org.cos30018.hets.negotiation.strategy;

/**
 * This class is the base class for every strategy that can be used in a
 * negotiation. It provides a simple method "getNewValue" to get the next value
 * independently from the actual implementation.
 *
 */
public abstract class Strategy implements Cloneable {

	public static final String STRATEGY_FIXED_PRICE = "Fixed Price";
	public static final String STRATEGY_RANDOM_TIT_FOR_TAT = "Random Tit-for-tat";
	public static final String STRATEGY_TIME_DEPENDENT = "Time-Dependent";
	public static final String STRATEGY_RESOURCE_DEPENDENT = "Resource-Dependent";

	protected double initialValue;
	protected double reservationValue;
	protected int round = 0;

	public Strategy(double initialValue, double reservationValue) {
		this.initialValue = initialValue;
		this.reservationValue = reservationValue;
	}

	/**
	 * This method has to be overriden by actual implementations of a strategy.
	 */
	public abstract double getNewValue(double constraints) throws DeadlineExceededException;

	public void reset(double initialValue) {
		this.initialValue = initialValue;
		this.round = 0;
	}

	public abstract String getName();

	public double getInitialValue() {
		return initialValue;
	}

	public double getReservationValue() {
		return reservationValue;
	}

	public int getRound() {
		return round;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
