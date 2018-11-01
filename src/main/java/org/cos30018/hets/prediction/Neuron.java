package org.cos30018.hets.prediction;

import java.util.ArrayList;
import java.util.List;


public class Neuron {

    private static final String ACTIVATION_FUNCTION = "ReLU";

    //list of neuron connections that lead to this one
    protected List<NeuronConnection> inputConnections;
    //list of neuron connections that this one leads to
    protected List<NeuronConnection> outputConnections;

    protected InputSummingFunction inputSummingFunction;
    protected ActivationFunction activationFunction;

    protected double output;

    protected double totalInput;

    private double error;

    private double bias;

    public Neuron()
    {
       inputConnections  = new ArrayList<>();
       outputConnections  = new ArrayList<>();

       switch (ACTIVATION_FUNCTION)
       {
           case "ReLU": activationFunction = new ReLUFunction();
           break;
           case "Linear": activationFunction = new LinearActivationFunction();
           break;
           case "Sigmoid": activationFunction = new SigmoidFunction();
       }

       inputSummingFunction = new WeightedSumFunction();

       bias = Math.random();

    }

    public void setValue(double v){
        output = v;
    }


    //Bias getter and setter
    public double getBias() { return bias; }

    public void setBias(double b) { bias = b; }

    public double getError() { return error; }

    public double getTotalInput() { return totalInput; }

    public List<NeuronConnection> getOutputConnections() { return outputConnections; }

    public void fire(){
        totalInput = inputSummingFunction.getOutput(inputConnections);

        output = activationFunction.calculateOutput(totalInput) + bias;
    }

    public void calculateOutputError(double expected){
        error = 0;
        if (outputConnections.isEmpty())
        {
            error = (expected - output)*activationFunction.calculateDerivative(output);
            //System.out.println("Error: "+error);
            //System.out.println("Output : " + output);
            //System.out.println("Activation function value: "  +activationFunction.calculateDerivative(output));
        }
    }

    public void backPropagateError()
    {
        error = 0;
        for (NeuronConnection n : outputConnections){
            error += (n.getWeight() * n.getToNeuron().getError()) * activationFunction.calculateDerivative(output);
            //System.out.println("Weight:" + n.getWeight());
            //System.out.println("From neuron error value: " + n.getFromNeuron().error);
        }

        error=error/outputConnections.size();
    }

    public double finalValue(){
        totalInput = inputSummingFunction.getOutput(inputConnections);
        output = totalInput*10 + bias;
        return output;
    }

    //add a connection (note that the new connection should be an INPUT neuron only
    public void addInputConnection(Neuron newNeuron){
        inputConnections.add(new NeuronConnection(newNeuron, this));
        newNeuron.addOutputConnection(this);
    }

    //calls by addOutputConnection, to the input neuron, adding itself as an output
    public void addOutputConnection(Neuron newNeuron){
        outputConnections.add(new NeuronConnection(this, newNeuron));
    }

}
