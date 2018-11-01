package org.cos30018.hets.prediction;

public class NeuronConnection {
    protected Neuron fromNeuron;
    protected Neuron toNeuron;
    protected double weight;


    //initial constructor
    public NeuronConnection(Neuron lFromNeuron, Neuron lToNeuron)
    {
        this.fromNeuron = lFromNeuron;
        this.toNeuron = lToNeuron;
        this.weight = Math.random();
    }

    //alternate weight constructor
    public NeuronConnection(Neuron fromNeuron, Neuron toNeuron, double weight)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = weight;
    }

    public void setWeight(double lWeight){
        this.weight = lWeight;
    }

    public double getWeight() { return this.weight; }

    public double getInput(){
       // return fromNeuron.CalculateOutput();
        return 0;
    }

    public double getWeightedInput(){
        double temp = fromNeuron.output;
        return fromNeuron.output * weight;
    }

    public Neuron getFromNeuron(){
        return fromNeuron;
    }

    public Neuron getToNeuron(){
        return toNeuron;
    }
}
