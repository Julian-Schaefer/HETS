package org.cos30018.hets.logic.appliance.forecast;

public abstract class UsageForecast {

	public abstract double[] calculateForecast(int period, int numberOfPeriods);

}
