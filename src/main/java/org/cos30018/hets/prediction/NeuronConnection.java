package prediction;

public class NeuronConnection {
    protected Neuron fromNeuron;
    protected Neuron toNeuron;
    protected double weight;


    //initial constructor
    public NeuronConnection(Neuron lFromNeuron, Neuron lToNeuron)
    {
        this.fromNeuron = lFromNeuron;
        this.toNeuron = lToNeuron;
        //this.weight = Math.random();
        this.weight = 2;
    }

    //alternate weight constructor
    public NeuronConnection(Neuron lFromNeuron, Neuron lToNeuron, double lWeight)
    {
        this.fromNeuron = lFromNeuron;
        this.toNeuron = lToNeuron;
        this.weight = lWeight;
    }

    public void setWeight(double lWeight){
        this.weight = lWeight;
    }

    public double getInput(){
       // return fromNeuron.CalculateOutput();
        return 0;
    }

    public double getWeightedInput(){
        double temp = fromNeuron.value;
        return fromNeuron.value * weight;
    }

    public Neuron getFromNeuron(){
        return fromNeuron;
    }

    public Neuron getToNeuron(){
        return toNeuron;
    }
}
