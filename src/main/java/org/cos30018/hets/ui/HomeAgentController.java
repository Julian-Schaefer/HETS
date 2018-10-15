package org.cos30018.hets.ui;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;

import jade.core.AID;

public class HomeAgentController implements HomeListener {

	private HomeAgentWindow view;
	
	private Home home;

	
	public HomeAgentController(Home home) {
		this.home = home;
		this.home.setListener(this);
		setupAppliancePanel();
	}
	
	private void setupAppliancePanel() {
		view = new HomeAgentWindow();		
	}

	@Override
	public void onApplianceAdded(AID applianceAID) {
		view.getAppliancePanel().addApplianceAgent(applianceAID);
	}

	@Override
	public void onApplianceRemoved(AID applianceAID) {
		view.getAppliancePanel().removeApplianceAgent(applianceAID);
	}

	@Override
	public void onRetailerAdded(AID retailerAID) {
		view.getRetailerPanel().addRetailerAgent(retailerAID);
	}

	@Override
	public void onRetailerRemoved(AID retailerAID) {
		view.getRetailerPanel().removeRetailerAgent(retailerAID);
	}

	@Override
	public void onTotalUsageForecastUpdated(double totalUsageForecast) {
		System.out.println("Total Usage updated: " + totalUsageForecast);
	}
}
