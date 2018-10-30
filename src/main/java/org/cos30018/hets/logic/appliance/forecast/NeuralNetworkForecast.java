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

		neuralNetworkManager.runNeuralNetwork(period+1);
		return new double[] { neuralNetworkManager.newPrediction };
	}

	@Override
	public double getLastActualUsage(int period, int numberOfPeriods){
		return neuralNetworkManager.actual;
	}
}
