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

	public double getVolumeChargeMinValue() {
		return volumeChargeMinValue;
	}

	public void setVolumeChargeMinValue(double volumeChargeMinValue) {
		this.volumeChargeMinValue = volumeChargeMinValue;
	}

	public double getVolumeChargeMaxValue() {
		return volumeChargeMaxValue;
	}

	public void setVolumeChargeMaxValue(double volumeChargeMaxValue) {
		this.volumeChargeMaxValue = volumeChargeMaxValue;
	}

	public double getFeedInRateMinValue() {
		return feedInRateMinValue;
	}

	public void setFeedInRateMinValue(double feedInRateMinValue) {
		this.feedInRateMinValue = feedInRateMinValue;
	}

	public double getFeedInRateMaxValue() {
		return feedInRateMaxValue;
	}

	public void setFeedInRateMaxValue(double feedInRateMaxValue) {
		this.feedInRateMaxValue = feedInRateMaxValue;
	}
}
