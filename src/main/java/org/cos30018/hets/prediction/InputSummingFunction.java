package prediction;

import java.util.List;

public interface InputSummingFunction {

    double GetOutput(List<NeuronConnection> inputConnections);
}
