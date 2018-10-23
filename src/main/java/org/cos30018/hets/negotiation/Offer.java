package org.cos30018.hets.negotiation;

import jade.core.AID;

/**
 * @author JSchaefer
 *
 */
public class Offer {

	private AID retailerId;
	private double price;
	private double amount;
	private int startPeriod;
	private int numberOfPeriods;
	private boolean refused;

	public Offer(AID retailerId, double price, double amount, int startPeriod, int numberOfPeriods) {
		this.retailerId = retailerId;
		this.price = price;
		this.startPeriod = startPeriod;
		this.numberOfPeriods = numberOfPeriods;
	}

	public Offer(double price, double amount, int startPeriod, int numberOfPeriods) {
		this.price = price;
		this.startPeriod = startPeriod;
		this.numberOfPeriods = numberOfPeriods;
	}

	private Offer() {

	}

	public static Offer refuse() {
		Offer offer = new Offer();
		offer.setRefused(true);
		return offer;
	}

	public Offer createCounterOffer() {
		return new Offer(price, amount, startPeriod, numberOfPeriods);
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public boolean isRefused() {
		return refused;
	}

	public void setRefused(boolean refused) {
		this.refused = refused;
	}

}
