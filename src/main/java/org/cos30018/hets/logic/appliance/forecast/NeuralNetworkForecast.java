package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.prediction.Predictor;

public class NeuralNetworkForecast extends UsageForecast {

	public NeuralNetworkForecast(Appliance appliance) {
		super(appliance);
	}

	@Override
	public double[] calculateForecast(int numberOfPeriods) {
		Predictor predictor = new Predictor(appliance.getType());
		predictor.RunNeuralNetwork(period);
		return new double[]  { predictor.newp };
	}	
}
