package org.cos30018.hets.negotiation.tariff;

public class RandomTariff extends Tariff {

	private double minValue;
	private double maxValue;

	public RandomTariff(double minValue, double maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public double getPrice(double requestedAmount, int period) {
		return (Math.random() * (maxValue - minValue)) + minValue;
	}

	@Override
	public String getName() {
		return TARIFF_RANDOM;
	}
}
