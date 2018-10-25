package org.cos30018.hets.negotiation.strategy;

public abstract class Strategy implements Cloneable {

	public static final String STRATEGY_FIXED_PRICE = "Fixed Price";
	public static final String STRATEGY_MODELLING = "Modelling";
	public static final String STRATEGY_TIME_DEPENDENT = "Time-Dependent";

	protected double initialValue;
	protected double reservationValue;
	protected int round = 1;

	public Strategy(double reservationValue) {
		this.reservationValue = reservationValue;
	}

	public abstract double getNewValue() throws DeadlineExceededException;

	public void reset(double initialValue) {
		this.initialValue = initialValue;
		this.round = 1;
	}

	public abstract String getName();

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
