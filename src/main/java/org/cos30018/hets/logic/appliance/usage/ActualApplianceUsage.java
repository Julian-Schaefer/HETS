package org.cos30018.hets.logic.appliance.usage;

import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.prediction.NeuralNetworkManager;

public class ActualApplianceUsage implements ActualUsage {

	private NeuralNetworkManager neuralNetworkManager;

	public ActualApplianceUsage(ApplianceType applianceType) {
		neuralNetworkManager = new NeuralNetworkManager(applianceType, false);
	}

	@Override
	public double getActualUsage(int period) {
		return neuralNetworkManager.getActualUsage(period);
	}
}
