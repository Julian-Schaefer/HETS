package prediction;

import java.util.List;

public final class WeightedSumFunction implements  InputSummingFunction{
    @Override
    public double GetOutput(List<NeuronConnection> inputConnections) {
        double weightedSum = 0d;
        for (NeuronConnection connection : inputConnections) {
            weightedSum += connection.getWeightedInput();
        }

        return weightedSum;
    }
}
