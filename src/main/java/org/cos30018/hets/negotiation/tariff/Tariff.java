package org.cos30018.hets.negotiation.tariff;

public abstract class Tariff {

	public static final String TARIFF_RANDOM = "Random Tariff";
	public static final String TARIFF_BLOCK = "Block Tariff";
	public static final String TARIFF_TIME_OF_USE = "Time of use Tariff";

	public abstract double getVolumeCharge(double requestedAmount, int period);

	public abstract double getFeedInCharge(double requestedAmount, int period);

	public abstract String getName();
}
