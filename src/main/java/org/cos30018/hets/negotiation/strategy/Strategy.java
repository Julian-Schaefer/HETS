package org.cos30018.hets.negotiation.strategy;

public abstract class Strategy implements Cloneable {

	public static final String STRATEGY_FIXED_PRICE = "Fixed Price";
	public static final String STRATEGY_MODELLING = "Modelling";
	public static final String STRATEGY_TIME_DEPENDENT = "Time-Dependent";
	public static final String STRATEGY_RESOURCE_DEPENDENT = "Resource-Dependent";

	protected double initialValue;
	protected double reservationValue;
	protected int round;

	public Strategy(double initialValue, double reservationValue) {
		this.initialValue = initialValue;
		this.reservationValue = reservationValue;
	}

	public abstract double getNewValue() throws DeadlineExceededException;

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
