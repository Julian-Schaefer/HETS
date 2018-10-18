package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.prediction.NeuralNetworkManager;

public class NeuralNetworkForecast extends UsageForecast {

	public NeuralNetworkForecast(Appliance appliance) {
		super(appliance);
	}

	@Override
	public double[] calculateForecast(int period, int numberOfPeriods) {
		NeuralNetworkManager neuralNetworkManager = new NeuralNetworkManager(appliance.getType());
		neuralNetworkManager.runNeuralNetwork(period);
		return new double[] { neuralNetworkManager.newPrediction };
	}
}
