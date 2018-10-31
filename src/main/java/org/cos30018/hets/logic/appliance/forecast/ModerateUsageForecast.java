package org.cos30018.hets.logic.appliance.forecast;

import java.util.LinkedList;
import java.util.List;

import org.cos30018.hets.logic.appliance.ActualApplianceUsage;

public class ModerateUsageForecast extends UsageForecast {

	private ActualApplianceUsage actualApplianceUsage;
	private List<Double> actualUsages = new LinkedList<>();

	public ModerateUsageForecast(ActualApplianceUsage actualApplianceUsage) {
		this.actualApplianceUsage = actualApplianceUsage;
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];

		double lastUsage = actualApplianceUsage.getActualUsage(period - 1);
		actualUsages.add(lastUsage);

		double sum = 0;
		for (double actualUsage : actualUsages) {
			sum += actualUsage;
		}

		double forecast = sum / actualUsages.size();

		for (int p = 0; p < numberOfPeriods; p++) {
			forecasts[p] = forecast;
		}

		return forecasts;
	}

}
