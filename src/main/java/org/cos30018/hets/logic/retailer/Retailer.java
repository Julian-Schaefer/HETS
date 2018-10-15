package org.cos30018.hets.logic.retailer;

public interface Retailer {

	public enum NegotiationStrategy {
		FIXED, COMPLEX
	}
	
	public enum PricingStrategy {
		HIGH, LOW, EFFICIENT
	}
}
