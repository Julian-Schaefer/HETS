package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.usage.ActualUsage;

public class SimpleUsageForecast implements UsageForecast {

	private ActualUsage actualUsage;

	public SimpleUsageForecast(ActualUsage actualUsage) {
		this.actualUsage = actualUsage;
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];
		double forecast = actualUsage.getActualUsage(period - 1);

		for (int p = 0; p < numberOfPeriods; p++) {
			forecasts[p] = forecast;
		}

		return forecasts;
	}
}
