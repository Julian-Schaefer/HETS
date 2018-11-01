package org.cos30018.hets.logic.appliance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cos30018.hets.logic.appliance.behaviour.ApplianceResponderBehaviour;
import org.cos30018.hets.logic.appliance.forecast.ModerateUsageForecast;
import org.cos30018.hets.logic.appliance.forecast.NeuralNetworkForecast;
import org.cos30018.hets.logic.appliance.forecast.SimpleUsageForecast;
import org.cos30018.hets.logic.appliance.forecast.UsageForecast;
import org.cos30018.hets.logic.common.RegisteringAgent;
import org.cos30018.hets.logic.home.HomeAgent;

public class ApplianceAgent extends RegisteringAgent implements Appliance {

	public static final int FORECASTING_SIMPLE = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2702213410686638092L;

	private List<ApplianceListener> listeners = new LinkedList<>();

	private ForecastingMethod forecastingMethod;
	private ApplianceType applianceType;
	private UsageForecast usageForecast;
	private ActualApplianceUsage actualApplianceUsage;

	private Map<Integer, Double> usageForecasts = new HashMap<>();
	private Map<Integer, Double> actualUsages = new HashMap<>();

	public ApplianceAgent() {
		super(HomeAgent.HOME_AGENT_SERVICE, ApplianceMessage.REGISTER, ApplianceMessage.UNREGISTER);
		registerO2AInterface(Appliance.class, this);
	}

	@Override
	protected void setup() {
		super.setup();

		Object[] arguments = getArguments();
		ApplianceType applianceType = (ApplianceType) arguments[0];
		setApplianceType(applianceType);
		ForecastingMethod forecastingMethod = (ForecastingMethod) arguments[1];
		setForecastingMethod(forecastingMethod);

		addBehaviour(new ApplianceResponderBehaviour(this));
	}

	@Override
	public void reset() {
		usageForecasts.clear();
		actualUsages.clear();

		for (ApplianceListener listener : listeners) {
			listener.onReset();
		}
	}

	@Override
	public void setApplianceType(ApplianceType applianceType) {
		this.applianceType = applianceType;
		actualApplianceUsage = new ActualApplianceUsage(applianceType);
	}

	@Override
	public ApplianceType getType() {
		return applianceType;
	}

	@Override
	public double getActualUsage(int period) {
		double actualUsage = actualApplianceUsage.getActualUsage(period);
		actualUsages.put(period, actualUsage);
		for (ApplianceListener listener : listeners) {
			listener.onNewActualUsage(period, actualUsage);
		}

		return actualUsage;
	}

	@Override
	public Map<Integer, Double> getActualUsages() {
		return actualUsages;
	}

	@Override
	public double[] getUsageForecast(int period, int numberOfPeriods) {
		double[] forecasts = usageForecast.calculateForecast(period, numberOfPeriods);
		for (int p = 0; p < numberOfPeriods; p++) {
			double forecast = forecasts[p];
			usageForecasts.put(period + p, forecast);
			for (ApplianceListener listener : listeners) {
				listener.onNewUsageForecast(period + p, forecast);
			}
		}

		return forecasts;
	}

	@Override
	public Map<Integer, Double> getUsageForecasts() {
		return usageForecasts;
	}

	@Override
	public void setForecastingMethod(ForecastingMethod forecastingMethod) {
		this.forecastingMethod = forecastingMethod;
		switch (forecastingMethod) {
		case SIMPLE:
			usageForecast = new SimpleUsageForecast(actualApplianceUsage);
			break;
		case MODERATE:
			usageForecast = new ModerateUsageForecast(actualApplianceUsage);
			break;
		case COMPLEX:
			usageForecast = new NeuralNetworkForecast(applianceType);
			break;
		}
	}

	@Override
	public ForecastingMethod getForecastingMethod() {
		return forecastingMethod;
	}

	@Override
	public void addListener(ApplianceListener listener) {
		listeners.add(listener);
	}
}
