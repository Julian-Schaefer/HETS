package prediction;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    protected String id;
    //list of neuron connections that lead to this one
    protected List<NeuronConnection> inputConnections;
    //list of neuron connections that this one leads to
    protected List<NeuronConnection> outputConnections;

    protected InputSummingFunction inputSummingFunction;
    protected ActivationFunction activationFunction;

    public double value;

    protected double bias;

    public Neuron()
    {
       inputConnections  = new ArrayList< >();
       outputConnections  = new ArrayList< >();

       activationFunction = new SigmoidFunction();
       inputSummingFunction = new WeightedSumFunction();
    }

    public void SetValue(double v){
        value = v;
    }


    public void Fire(){
        double totalInput = inputSummingFunction.GetOutput(inputConnections);

        value = activationFunction.calculateOutput(totalInput);
    }

    public double Final(){
        double totalInput = inputSummingFunction.GetOutput(inputConnections);
        value = totalInput;
        return value;
    }

    //add a connection (note that the new connection should be an INPUT neuron only
    public void AddInputConnection(Neuron newNeuron){
        inputConnections.add(new NeuronConnection(newNeuron, this));
        newNeuron.AddOutputConnection(this);
    }

    //calls by AddOutputConnection, to the input neuron, adding itself as an output
    public void AddOutputConnection(Neuron newNeuron){
        outputConnections.add(new NeuronConnection(this, newNeuron));
    }

}
