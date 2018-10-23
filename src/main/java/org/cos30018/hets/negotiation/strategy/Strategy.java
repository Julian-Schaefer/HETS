package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.tariff.Tariff;

public abstract class Strategy {

	protected Tariff tariff;

	public Strategy(Tariff tariff) {
		this.tariff = tariff;
	}

	public abstract Offer getCounterOffer(Offer incomingOffer);

}
