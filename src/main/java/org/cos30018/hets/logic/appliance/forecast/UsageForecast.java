package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.Appliance;

public abstract class UsageForecast {

	protected Appliance appliance;

	public UsageForecast(Appliance appliance) {
		this.appliance = appliance;
	}

	public abstract double[] calculateForecast(int period, int numberOfPeriods);
}
