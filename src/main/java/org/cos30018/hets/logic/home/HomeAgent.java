package org.cos30018.hets.logic.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.cos30018.hets.logic.home.behaviour.ApplianceForecastRequestBehaviour;
import org.cos30018.hets.logic.home.behaviour.ApplianceUsageRequestBehaviour;
import org.cos30018.hets.logic.home.behaviour.HomeAgentNegotiationBehaviour;
import org.cos30018.hets.logic.home.behaviour.HomeAgentRegisterRespondBehaviour;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.strategy.TimeDependentStrategy;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HomeAgent extends Agent implements Home {

	public static final String HOME_AGENT_SERVICE = "home_agent";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4685491195096413651L;

	private List<HomeListener> listeners = new LinkedList<>();

	private List<AID> applianceAIDs = new ArrayList<>();
	private List<AID> retailerAIDs = new ArrayList<>();

	private int period;
	private int forecastPeriodCount = 0;
	private double excessPrice = 150.0;

	private Map<Integer, Double> usageForecasts = new HashMap<>();
	private Map<Integer, Double> actualUsages = new HashMap<>();

	private Map<Integer, Offer> negotiatedOffers = new HashMap<>();

	private Strategy buyingStrategy = new TimeDependentStrategy(20, 0, 50, 1);
	private Strategy sellingStrategy = new TimeDependentStrategy(20, 30, 0, 1);

	public HomeAgent() {
		registerO2AInterface(Home.class, this);
	}

	@Override
	protected void setup() {
		reset();

		ServiceDescription sd = new ServiceDescription();
		sd.setType(HOME_AGENT_SERVICE);
		sd.setName(getLocalName());
		register(sd);

		addBehaviour(new HomeAgentRegisterRespondBehaviour(this));
	}

	public void registerAppliance(AID applianceAID) {
		applianceAIDs.add(applianceAID);
		notifyListeners(listener -> listener.onApplianceAdded(applianceAID));
	}

	public void unregisterAppliance(AID applianceAID) {
		applianceAIDs.remove(applianceAID);
		notifyListeners(listener -> listener.onApplianceRemoved(applianceAID));
	}

	public void registerRetailer(AID retailerAID) {
		retailerAIDs.add(retailerAID);
		notifyListeners(listener -> listener.onRetailerAdded(retailerAID));
	}

	public void unregisterRetailer(AID retailerAID) {
		retailerAIDs.remove(retailerAID);
		notifyListeners(listener -> listener.onRetailerRemoved(retailerAID));
	}

	private void register(ServiceDescription serviceDescription) {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		dfAgentDescription.setName(getAID());
		dfAgentDescription.addServices(serviceDescription);

		try {
			DFService.register(this, dfAgentDescription);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (Exception e) {
		}
	}

	@Override
	public void reset() {
		period = START_PERIOD - 1;

		usageForecasts.clear();
		actualUsages.clear();
		negotiatedOffers.clear();
	}

	@Override
	public List<AID> getAppliances() {
		return applianceAIDs;
	}

	@Override
	public List<AID> getRetailers() {
		return retailerAIDs;
	}

	@Override
	public void setForecastPeriodCount(int forecastPeriodCount) {
		this.forecastPeriodCount = forecastPeriodCount;
	}

	@Override
	public int getForecastPeriodCount() {
		return forecastPeriodCount;
	}

	@Override
	public void setTotalUsageForecast(int period, double totalUsageForecast) {
		usageForecasts.put(period, totalUsageForecast);
		notifyListeners(listener -> listener.onTotalUsageForecastUpdated(period, totalUsageForecast));

		addBehaviour(HomeAgentNegotiationBehaviour.create(this));
	}

	@Override
	public double getTotalUsageForecast(int period) {
		if (usageForecasts.containsKey(period)) {
			return usageForecasts.get(period);
		}

		return 0;
	}

	@Override
	public Map<Integer, Double> getTotalUsageForecasts() {
		return usageForecasts;
	}

	@Override
	public void setActualTotalUsage(int period, double lastActualTotalUsage) {
		actualUsages.put(period, lastActualTotalUsage);
		notifyListeners(listener -> listener.onActualTotalUsageUpdated(period, lastActualTotalUsage));
	}

	@Override
	public double getActualTotalUsage(int period) {
		if (actualUsages.containsKey(period)) {
			return actualUsages.get(period);
		}

		return 0;
	}

	@Override
	public Map<Integer, Double> getActualTotalUsages() {
		return actualUsages;
	}

	@Override
	public void setNegotiatedOffer(int period, Offer offer) {
		negotiatedOffers.put(period, offer);
		notifyListeners(listener -> listener.onNewNegotiatedOffer(period, offer));
	}

	@Override
	public Map<Integer, Offer> getNegotiatedOffers() {
		return negotiatedOffers;
	}

	@Override
	public void setPeriod(int period) {
		this.period = period;
		if (period > START_PERIOD) {
			addBehaviour(ApplianceUsageRequestBehaviour.create(this, getAppliances()));
		}
		addBehaviour(ApplianceForecastRequestBehaviour.create(this, getAppliances()));

		notifyListeners(listener -> listener.onNewPeriod());
	}

	@Override
	public void nextPeriod() {
		setPeriod(period + 1);
	}

	@Override
	public int getCurrentPeriod() {
		return period;
	}

	@Override
	public int getNextPeriod() {
		return period + 1;
	}

	@Override
	public Strategy getBuyingStrategy() {
		return buyingStrategy;
	}

	@Override
	public void setBuyingStrategy(Strategy strategy) {
		this.buyingStrategy = strategy;
	}

	@Override
	public Strategy getSellingStrategy() {
		return sellingStrategy;
	}

	@Override
	public void setSellingStrategy(Strategy strategy) {
		this.sellingStrategy = strategy;
	}

	@Override
	public void setExcessPrice(double price) {
		this.excessPrice = price;
	}

	@Override
	public double getExcessPrice() {
		return excessPrice;
	}

	@Override
	public synchronized void addListener(HomeListener listener) {
		listeners.add(listener);
	}

	private synchronized void notifyListeners(Consumer<? super HomeListener> consumer) {
		listeners.forEach(listener -> consumer.accept(listener));
	}
}
