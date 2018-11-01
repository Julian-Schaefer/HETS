package org.cos30018.hets.logic.appliance;

import java.util.Map;

public interface Appliance {
	public enum ApplianceType {
		DISHWASHER, FRIDGE, WASHING_MACHINE, DRYER, HEAT_PUMP, HOT_WATER_SYSTEM, HIFI, SOLARPANEL
	}

	public enum ForecastingMethod {
		SIMPLE, MODERATE, COMPLEX, SOLARPANEL
	}

	String getLocalName();

	void setApplianceType(ApplianceType applianceType);

	ApplianceType getType();

	double getActualUsage(int period);

	Map<Integer, Double> getActualUsages();

	double[] getUsageForecast(int period, int numberOfPeriods);

	Map<Integer, Double> getUsageForecasts();

	void setForecastingMethod(ForecastingMethod forecastingMethod);

	ForecastingMethod getForecastingMethod();

	void addListener(ApplianceListener listener);

	public interface ApplianceListener {
		void onNewActualUsage(int period, double usage);

		void onNewUsageForecast(int period, double forecast);
	}
}
