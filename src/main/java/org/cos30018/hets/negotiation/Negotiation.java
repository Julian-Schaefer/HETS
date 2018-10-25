package org.cos30018.hets.negotiation;

import org.cos30018.hets.negotiation.strategy.DeadlineExceededException;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.utility.OfferUtility;

public class Negotiation {

	private Strategy strategy;
	private OfferUtility utility;

	private Offer lastOutgoingOffer;

	public Negotiation(Strategy strategy, OfferUtility utility) {
		this.strategy = strategy;
		this.utility = utility;
	}

	public Offer createCounterOffer(Offer incomingOffer) {
		try {
			if (lastOutgoingOffer == null) {
				Offer counterOffer = incomingOffer.createCounterOffer();
				counterOffer.setPrice(strategy.getNewValue());
				return counterOffer;
			}

			double incomingOfferUtility = utility.getUtility(incomingOffer);
			double lastOutgoingOfferUtility = utility.getUtility(lastOutgoingOffer);
			if (incomingOfferUtility >= lastOutgoingOfferUtility) {
				return Offer.accept();
			} else {
				Offer counterOffer = incomingOffer.createCounterOffer();
				counterOffer.setPrice(strategy.getNewValue());
				return counterOffer;
			}
		} catch (DeadlineExceededException e) {
			return Offer.refuse();
		}
	}

	public int getRound() {
		return strategy.getRound();
	}
}
