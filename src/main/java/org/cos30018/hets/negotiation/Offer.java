package org.cos30018.hets.negotiation;

import java.io.Serializable;

import jade.core.AID;

/**
 * This class is just a model object without actual logic. It contains the
 * offers and can be serialized. That way, we can easily send ACLMessages
 * between different agents by leveraging the Serializable Interface
 *
 */
public class Offer implements Serializable {

	public enum Status {
		ACCEPT, REFUSE, COUNTEROFFER
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5693041360119918618L;

	private AID retailerId;
	private double price;
	private double amount;
	private int startPeriod;
	private int numberOfPeriods;
	private Status status;
	private double excessPrice = 150.0;

	public Offer(AID retailerId, double price, double amount, int startPeriod, int numberOfPeriods) {
		this.retailerId = retailerId;
		this.price = price;
		this.amount = amount;
		this.startPeriod = startPeriod;
		this.numberOfPeriods = numberOfPeriods;
		this.status = Status.COUNTEROFFER;
	}

	public Offer(double price, double amount, int startPeriod, int numberOfPeriods) {
		this(null, price, amount, startPeriod, numberOfPeriods);
	}

	private Offer() {

	}

	public static Offer accept() {
		Offer offer = new Offer();
		offer.setStatus(Status.ACCEPT);
		return offer;
	}

	public static Offer refuse() {
		Offer offer = new Offer();
		offer.setStatus(Status.REFUSE);
		return offer;
	}

	public Offer createCounterOffer() {
		return new Offer(price, amount, startPeriod, numberOfPeriods);
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Price: ").append(price).append(", ").append("Amount: ").append(amount)
				.append(", ").append("Number of Periods: ").append(numberOfPeriods).append(", ").append("Status:")
				.append(status).toString();
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getExcessPrice() {
		return excessPrice;
	}

	public void setExcessPrice(double excessPrice) {
		this.excessPrice = excessPrice;
	}
}
