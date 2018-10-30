package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.Appliance;

public class SimpleUsageForecast extends UsageForecast {

	public SimpleUsageForecast(Appliance appliance) {
		super(appliance);
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];

		for (int p = 0; p < numberOfPeriods; p++) {
			forecasts[p] = period;
		}

		return forecasts;
	}

	@Override
	public double getLastActualUsage(int period, int numberOfPeriods)
	{
		return 0.0;
	}
}
