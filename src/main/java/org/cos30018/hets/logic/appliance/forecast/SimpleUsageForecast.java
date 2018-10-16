package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.Appliance;

public class SimpleUsageForecast extends UsageForecast {

	public SimpleUsageForecast(Appliance appliance) {
		super(appliance);
	}

	@Override
	public double[] calculateForecast(int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];

		for (int period = 0; period < numberOfPeriods; period++) {
			forecasts[period] = appliance.getLastActualUsage() + 2;
		}

		return forecasts;
	}
}
