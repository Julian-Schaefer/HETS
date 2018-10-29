package org.cos30018.hets.negotiation.tariff;

public class RandomTariff extends Tariff {

	private double volumeChargeMinValue;
	private double volumeChargeMaxValue;
	private double feedInRateMinValue;
	private double feedInRateMaxValue;

	public RandomTariff(double volumeChargeMinValue, double volumeChargeMaxValue, double feedInRateMinValue,
			double feedInRateMaxValue) {
		this.volumeChargeMinValue = volumeChargeMinValue;
		this.volumeChargeMaxValue = volumeChargeMaxValue;
		this.feedInRateMinValue = feedInRateMinValue;
		this.feedInRateMaxValue = feedInRateMaxValue;
	}

	@Override
	public double getVolumeCharge(double requestedAmount, int period) {
		return (Math.random() * (volumeChargeMaxValue - volumeChargeMinValue)) + volumeChargeMinValue;
	}

	@Override
	public double getFeedInCharge(double requestedAmount, int period) {
		return (Math.random() * (feedInRateMaxValue - feedInRateMinValue)) + feedInRateMinValue;
	}

	@Override
	public String getName() {
		return TARIFF_RANDOM;
	}
}
