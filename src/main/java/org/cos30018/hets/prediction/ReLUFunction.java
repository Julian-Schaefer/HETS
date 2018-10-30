package org.cos30018.hets.prediction;

public class ReLUFunction implements ActivationFunction {
    //Rectified Linear Unit
    @Override
    public double calculateOutput(double x){
        if (x > 0) {
            return x;
        }
        else{
            return 0;
        }
    }

    @Override
    public double calculateDerivative(double x) {
        if (x >= 0) {
            return 1;
        }
        else{
            return 0;
        }
    }
}
