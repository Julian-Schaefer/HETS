package org.cos30018.hets.negotiation.tariff;

/**
 * This class represents retailer tariffs. The actual tariffs can differentiate
 * but the basics are the same. Therefore, this abstract class provides a
 * general interface to get the volume charge and the feed-in-rate for a
 * specified amount and period
 */
public abstract class Tariff {

	public static final String TARIFF_RANDOM = "Random Tariff";
	public static final String TARIFF_BLOCK = "Block Tariff";
	public static final String TARIFF_TIME_OF_USE = "Time of use Tariff";

	public abstract double getVolumeCharge(double requestedAmount, int period);

	public abstract double getFeedInRate(double requestedAmount, int period);

	public abstract String getName();
}
