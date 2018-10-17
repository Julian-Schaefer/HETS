package org.cos30018.hets.logic.home;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.cos30018.hets.logic.home.behaviour.ApplianceForecastRequestBehaviour;
import org.cos30018.hets.logic.home.behaviour.ApplianceUsageRequestBehaviour;
import org.cos30018.hets.logic.home.behaviour.HomeAgentRegisterRespondBehaviour;
import org.cos30018.hets.logic.home.behaviour.RetailerRequestBehaviour;

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

	private int period = 0;
	private int forecastPeriodCount;

	private double lastActualTotalUsage;
	private double totalUsageForecast;

	private long intervalPeriod = 5000;

	public HomeAgent() {
		registerO2AInterface(Home.class, this);
	}

	@Override
	protected void setup() {
		ServiceDescription sd = new ServiceDescription();
		sd.setType(HOME_AGENT_SERVICE);
		sd.setName(getLocalName());
		register(sd);

		addBehaviour(new HomeAgentRegisterRespondBehaviour(this));
	}

	public void registerAppliance(AID applianceAID) {
		applianceAIDs.add(applianceAID);
		for (HomeListener listener : listeners)
			listener.onApplianceAdded(applianceAID);
	}

	public void unregisterAppliance(AID applianceAID) {
		applianceAIDs.remove(applianceAID);
		for (HomeListener listener : listeners)
			listener.onApplianceRemoved(applianceAID);
	}

	public void registerRetailer(AID retailerAID) {
		retailerAIDs.add(retailerAID);
		for (HomeListener listener : listeners)
			listener.onRetailerAdded(retailerAID);
	}

	public void unregisterRetailer(AID retailerAID) {
		retailerAIDs.remove(retailerAID);
		for (HomeListener listener : listeners)
			listener.onRetailerRemoved(retailerAID);
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
	public List<AID> getAppliances() {
		return applianceAIDs;
	}

	@Override
	public List<AID> getRetailers() {
		return retailerAIDs;
	}

	@Override
	public void setIntervalPeriod(long period) {
		this.intervalPeriod = period;
	}

	@Override
	public long getIntervalPeriod() {
		return intervalPeriod;
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
	public void setTotalUsageForecast(double totalUsageForecast) {
		this.totalUsageForecast = totalUsageForecast;
		for (HomeListener listener : listeners) {
			listener.onTotalUsageForecastUpdated(totalUsageForecast);
		}
		addBehaviour(RetailerRequestBehaviour.create(this));
	}

	@Override
	public double getTotalUsageForecast() {
		return totalUsageForecast;
	}

	@Override
	public void setLastActualTotalUsage(double lastActualTotalUsage) {
		this.lastActualTotalUsage = lastActualTotalUsage;
		for (HomeListener listener : listeners) {
			listener.onLastActualTotalUsageUpdated(lastActualTotalUsage);
		}
	}

	@Override
	public double getLastActualTotalUsage() {
		return lastActualTotalUsage;
	}

	@Override
	public void addListener(HomeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void setPeriod(int period) {
		this.period = period;
		addBehaviour(ApplianceUsageRequestBehaviour.create(this, getAppliances()));
		addBehaviour(ApplianceForecastRequestBehaviour.create(this, getAppliances()));
	}

	@Override
	public void nextPeriod() {
		setPeriod(period + 1);
	}

	@Override
	public int getPeriod() {
		return period;
	}
}
