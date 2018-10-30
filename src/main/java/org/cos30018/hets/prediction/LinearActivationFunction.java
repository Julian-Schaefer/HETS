package org.cos30018.hets.prediction;

public class LinearActivationFunction implements  ActivationFunction{
    @Override
    public double calculateOutput(double x){
        return x;
    }

    @Override
    public double calculateDerivative(double x){
        return 1;
    }
}