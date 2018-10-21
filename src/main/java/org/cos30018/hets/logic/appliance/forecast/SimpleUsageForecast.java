package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.ActualApplianceUsage;
import org.cos30018.hets.logic.appliance.Appliance;

public class SimpleUsageForecast extends UsageForecast {

	private ActualApplianceUsage actualApplianceUsage;

	public SimpleUsageForecast(Appliance appliance) {
		super(appliance);
		this.actualApplianceUsage = new ActualApplianceUsage(appliance.getType());
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];

		for (int p = 0; p < numberOfPeriods; p++) {
			forecasts[p] = actualApplianceUsage.getActualUsage(period + p) + period;
		}

		return forecasts;
	}
}
