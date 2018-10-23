package org.cos30018.hets.negotiation.tariff;

public abstract class Tariff {

	public abstract double getPrice(double requestedUsage, int period);

}
