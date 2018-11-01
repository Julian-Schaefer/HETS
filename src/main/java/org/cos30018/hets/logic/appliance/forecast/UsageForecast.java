package org.cos30018.hets.logic.appliance.forecast;

public interface UsageForecast {

	double[] calculateForecast(int period, int numberOfPeriods);

}
