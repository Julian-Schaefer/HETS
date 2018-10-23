package org.cos30018.hets.negotiation.tariff;

public class RandomTariff extends Tariff {

	@Override
	public double getPrice(double requestedAmount, int period) {
		return Math.random() * 70;
	}

}
