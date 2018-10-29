package org.cos30018.hets.negotiation.tariff;

public class TimeOfUseTariff extends Tariff {

	private double[] volumeCharges;
	private double[] feedInRates;

	public TimeOfUseTariff(double[] volumeCharges, double[] feedInRates) {
		this.volumeCharges = volumeCharges;
		this.feedInRates = feedInRates;
	}

	@Override
	public double getVolumeCharge(double requestedAmount, int period) {
		return volumeCharges[period % 24];
	}

	@Override
	public double getFeedInCharge(double requestedAmount, int period) {
		return feedInRates[period % 24];
	}

	@Override
	public String getName() {
		return TARIFF_TIME_OF_USE;
	}

	public double[] getVolumeCharges() {
		return volumeCharges;
	}

	public void setVolumeCharges(double[] volumeCharges) {
		this.volumeCharges = volumeCharges;
	}

	public double[] getFeedInRates() {
		return feedInRates;
	}

	public void setFeedInRates(double[] feedInRates) {
		this.feedInRates = feedInRates;
	}
}
