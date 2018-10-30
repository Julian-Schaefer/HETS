package org.cos30018.hets.prediction;

import java.util.ArrayList;
import java.util.List;

public class NeuronLayer {
    private List<Neuron> neurons;

    public NeuronLayer(int size){
        neurons = new ArrayList<>();
        for (int i = 0; i < size ; i++) {
            neurons.add(new Neuron());
        }
    }

    public List<Neuron> getNeurons(){
        return neurons;
    }

    public void fireLayer(){
        for(Neuron n : neurons)
        {
            n.fire();
        }
    }

    public List<Double> finalValue(){
        List<Double> returnValue = new ArrayList<>();
        for(Neuron n : neurons)
        {
            returnValue.add(n.finalValue());
        }
        return  returnValue;
    }

    public void calculateOutputError(double actual)
    {
        for(Neuron n : neurons)
        {
            n.calculateOutputError(actual);
        }
    }

    public void backPropagateError()
    {
        for(Neuron n : neurons)
        {
            n.backPropagateError();
        }
    }

    public void trainWeights(double learningRate)
    {
        for (Neuron n : neurons){
            List<NeuronConnection> connections = n.getOutputConnections();
            for (NeuronConnection c : connections){
                c.setWeight(c.getWeight() + (learningRate * n.getError() * n.getTotalInput()));
            }
        }
    }

    public void trainBiases(double learningRate)
    {
        for (Neuron n : neurons) {
            n.setBias(n.getBias() + (learningRate * n.getError()) );
        }
    }

    public void updateNeuronValue(int index, int value)
    {
        neurons.get(index).setValue(value);
    }
}