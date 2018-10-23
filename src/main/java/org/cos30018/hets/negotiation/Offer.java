package org.cos30018.hets.negotiation;

import jade.core.AID;

public class Offer {

	private AID retailerId;
	private double price;
	private int startPeriod;
	private int numberOfPeriods;

	public Offer(AID retailerId, double price, int startPeriod, int numberOfPeriods) {
		this.retailerId = retailerId;
		this.price = price;
		this.startPeriod = startPeriod;
		this.numberOfPeriods = numberOfPeriods;
	}

	public AID getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(AID retailerId) {
		this.retailerId = retailerId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(int startPeriod) {
		this.startPeriod = startPeriod;
	}

	public int getNumberOfPeriods() {
		return numberOfPeriods;
	}

	public void setNumberOfPeriods(int numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
	}
}
