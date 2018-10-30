package org.cos30018.hets.prediction;

public interface ActivationFunction {
    double calculateOutput(double summedInput);
    double calculateDerivative(double value);
}
