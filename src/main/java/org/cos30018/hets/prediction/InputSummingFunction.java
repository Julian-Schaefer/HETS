package org.cos30018.hets.prediction;

import java.util.List;

public interface InputSummingFunction {

    double getOutput(List<NeuronConnection> inputConnections);
}
