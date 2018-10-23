package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.prediction.NeuralNetworkManager;

public class NeuralNetworkForecast extends UsageForecast {

	private NeuralNetworkManager neuralNetworkManager;

	public NeuralNetworkForecast(Appliance appliance) {
		super(appliance);
		neuralNetworkManager = new NeuralNetworkManager(appliance.getType());
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		double[] forecasts = new double[numberOfPeriods];

		for (int p = 0; p < numberOfPeriods; p++) {
			neuralNetworkManager.runNeuralNetwork(period + p);
			forecasts[p] = neuralNetworkManager.newPrediction;
		}

		return forecasts;
	}
}
