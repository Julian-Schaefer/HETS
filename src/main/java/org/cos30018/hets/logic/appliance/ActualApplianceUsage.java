package org.cos30018.hets.logic.appliance;

import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.prediction.NeuralNetworkManager;

public class ActualApplianceUsage {

	private NeuralNetworkManager neuralNetworkManager;

	public ActualApplianceUsage(ApplianceType applianceType) {
		neuralNetworkManager = new NeuralNetworkManager(applianceType, false);

	}

	public double getActualUsage(int period) {
		return neuralNetworkManager.getActualUsage(period);
	}
}
