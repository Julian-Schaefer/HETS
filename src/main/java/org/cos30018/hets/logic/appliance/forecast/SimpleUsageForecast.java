package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.ActualApplianceUsage;

public class SimpleUsageForecast implements UsageForecast {

	private ActualApplianceUsage actualApplianceUsage;

	public SimpleUsageForecast(ActualApplianceUsage actualApplianceUsage) {
		this.actualApplianceUsage = actualApplianceUsage;
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];
		double forecast = actualApplianceUsage.getActualUsage(period - 1);

		for (int p = 0; p < numberOfPeriods; p++) {
			forecasts[p] = forecast;
		}

		return forecasts;
	}
}
