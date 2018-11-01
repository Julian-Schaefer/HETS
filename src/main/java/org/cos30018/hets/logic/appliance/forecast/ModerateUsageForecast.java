package org.cos30018.hets.logic.appliance.forecast;

import java.util.LinkedList;
import java.util.List;

import org.cos30018.hets.logic.appliance.usage.ActualUsage;

public class ModerateUsageForecast implements UsageForecast {

	private ActualUsage actualUsage;
	private List<Double> actualUsages = new LinkedList<>();

	public ModerateUsageForecast(ActualUsage actualUsage) {
		this.actualUsage = actualUsage;
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];

		double lastUsage = actualUsage.getActualUsage(period - 1);
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
