package org.cos30018.hets.negotiation.utility;

/*
 * This class is used to calculate the utility of a price.
 * It maps the value of maxValue to maxEvaluation and the value of minValue to minEvaluation.
 * Therefore, the getUtility method accepts values in the range of minValue to maxValue and maps them
 * the corresponding evaluation in the range of minEvaluation to maxEvalutaion.
 * (minEvaluation and maxEvaluation should be values between 0 and 1)
 */
public class PriceUtility extends CardinalUtility<Double> {

	private double conversionRate;
	private double offset;

	public PriceUtility(double minValue, double maxValue, double minEvaluation, double maxEvaluation) {
		conversionRate = (maxEvaluation - minEvaluation) / (maxValue - minValue);
		offset = maxEvaluation - conversionRate * maxValue;
	}

	@Override
	public double getUtility(Double input) {
		return conversionRate * input + offset;
	}

}
