package prediction;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private List<NeuronLayer> layers;
    private List<Integer> trainingData;
    private List<List<Integer>> input;

    private List<List<Double>> CostValues = new ArrayList<>();


    public void Train(List<Integer> lTrainingData, List<List<Integer>> lInput, int hiddenLayers, int layerSize){
        //reset the neural network
        trainingData = new ArrayList<>();
        input = new ArrayList<>();
        layers = new ArrayList<>();

        trainingData = lTrainingData;
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
            for (Neuron n : layers.get(i).GetNeurons()){
                for (Neuron m : layers.get(i+1).GetNeurons()){
                    m.AddInputConnection(n);
                }
            }
        }

       // layers.get(0).UpdateNeuronValue(0,0);

       // NeuronLayer initialTemp = layers.get(0);

        for (int i = 0; i < trainingData.size(); i++) {

                for (int j = 0; j < lInput.size(); j++) {
                    for (List<Integer> inData : input) {

                        //initialTemp.UpdateNeuronValue(j, inData.get(j));
                        layers.get(0).GetNeurons().get(j).SetValue(inData.get(j));
                    }
                }

            for (int k = 0; k < hiddenLayers; k++) {
                layers.get(k + 1).FireLayer();
            }

            layers.get(layers.size() - 1).Final();

            if ((i+1)%20000==0) {
                System.out.println((i+1) + " data values have been processed");
            }
        }
    }

    public Integer Run(List<Integer> inputs){

        for (int i = 0; i < inputs.size(); i++) {
            layers.get(0).GetNeurons().get(i).SetValue(inputs.get(i));
        }

        for (int k = 0; k < layers.size()-2; k++)
        {
            layers.get(k+1).FireLayer();
        }

        layers.get(layers.size()-1).Final();

        List<Double> finalValues = layers.get(layers.size()-1).Final();

        return (int)Math.round(finalValues.get(0));

    }
    //input training data, the data for the input layer, and the number of hidden layers required
    public NeuralNetwork()
    {
    }

}
