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

    public List<Neuron> GetNeurons(){
        return neurons;
    }

    public void FireLayer(){
        for(Neuron n : neurons)
        {
            n.Fire();
        }
    }

    public List<Double> Final(){
        List<Double> returnValue = new ArrayList<>();
        for(Neuron n : neurons)
        {
            returnValue.add(n.Final());
        }
        return  returnValue;
    }

    public void UpdateNeuronValue(int index, int value)
    {
        neurons.get(index).SetValue(value);
    }
}