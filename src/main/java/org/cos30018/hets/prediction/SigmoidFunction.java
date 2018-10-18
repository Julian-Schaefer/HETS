package org.cos30018.hets.prediction;

public final class SigmoidFunction implements ActivationFunction{
    @Override
    public double calculateOutput(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
