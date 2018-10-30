package org.cos30018.hets.prediction;

import java.util.List;

public final class WeightedSumFunction implements  InputSummingFunction{
    @Override
    public double getOutput(List<NeuronConnection> inputConnections) {
        double weightedSum = 0d;
        for (NeuronConnection connection : inputConnections) {
            weightedSum += connection.getWeightedInput();
        }

        return weightedSum/inputConnections.size();
    }
}
