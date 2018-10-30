package org.cos30018.hets.prediction;

//Implementation of the Sigmoid activatication function, to place weight values
//in the range of 0-1;
public final class SigmoidFunction implements ActivationFunction{
    @Override
    public double calculateOutput(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    @Override
    public double calculateDerivative(double x){
        if (x==1)
        {
            return 0;
        }
        else
        {
            return x*(1.0 - x);
        }
    }
}
