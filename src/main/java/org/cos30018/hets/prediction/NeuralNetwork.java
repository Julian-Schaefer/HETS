package org.cos30018.hets.prediction;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private List<NeuronLayer> layers;
    private List<Double> trainingDataActual;
    private List<List<Double>> input;

    public static final double LEARNING_RATE = 0.000001;


    public void train(List<Double> lTrainingData, List<List<Double>> lInput, int hiddenLayers, int layerSize, int sessions){
        //reset the neural network
        trainingDataActual = new ArrayList<>();
        input = new ArrayList<>();
        layers = new ArrayList<>();

        trainingDataActual = lTrainingData;
        input = lInput;

        //set hiddenLayer size + input and output layers
        //initiate input layer, with size the same as the number of inputs
        layers.add(new NeuronLayer(lInput.size()));

        //add number of required hidden layers
        for (int i = 0; i < hiddenLayers; i++) {
            layers.add(new NeuronLayer(layerSize));
        }

        //add the output layer
        layers.add(new NeuronLayer(1));

        System.out.println("There are currently "   + layers.size() + " layers.");

        //connect layers
        for (int i = 0; i < layers.size()-1; i++)
        {
            for (Neuron n : layers.get(i).getNeurons()){
                for (Neuron m : layers.get(i+1).getNeurons()){
                    m.addInputConnection(n);
                }
            }
        }

        // layers.get(0).updateNeuronValue(0,0);

        // NeuronLayer initialTemp = layers.get(0);

        for (int s = 0; s < sessions; s++) {
            for (int i = 0; i < trainingDataActual.size() - 1; i++) {
                //input values to input layer
                for (int j = 0; j < lInput.size(); j++) {
                    for (List<Double> inData : input) {

                        //initialTemp.updateNeuronValue(j, inData.get(j));
                        layers.get(0).getNeurons().get(j).setValue(inData.get(j));
                    }
                }

                //fire hidden layer
                for (int k = 0; k < hiddenLayers; k++) {
                    layers.get(k + 1).fireLayer();
                }

                //fire output layer
                layers.get(layers.size() - 1).finalValue();

                //calculate error in final layer using the real value
                // TODO get rid of i+1 here by shifting the input data by 1
                layers.get(layers.size() - 1).calculateOutputError(trainingDataActual.get(i + 1));

                for (int l = layers.size() - 2; l >= 0; l--) {
                    layers.get(l).backPropagateError();
                }

                if ((i + 2) % 20000 == 0) {
                    System.out.println((i + 1) + " data values have been processed");
                }

                for (int m = 0; m < layers.size(); m++) {
                    layers.get(m).trainWeights(LEARNING_RATE);
                    layers.get(m).trainBiases(LEARNING_RATE);
                }
            }

            System.out.println("Training Session " + (s+1) + "/" + sessions + " Complete");
        }
    }

    public Double run(List<Double> inputs){

        for (int i = 0; i < inputs.size(); i++) {
            layers.get(0).getNeurons().get(i).setValue(inputs.get(i));
        }

        for (int k = 0; k < layers.size()-2; k++)
        {
            layers.get(k+1).fireLayer();
        }

        layers.get(layers.size()-1).finalValue();

        List<Double> finalValues = layers.get(layers.size()-1).finalValue();

        return finalValues.get(0);

    }
    //input training data, the data for the input layer, and the number of hidden layers required
    public NeuralNetwork()
    {
    }

}
