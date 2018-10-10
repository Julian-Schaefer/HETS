package org.cos30018.hets.ui;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;
import org.cos30018.hets.ui.appliance.AppliancePanelController;

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
		view.getAppliancePanel().addApplianceAgent(applianceAID.getLocalName());
	}

	@Override
	public void onApplianceRemoved(AID applianceAID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRetailerAdded(AID retailerAID) {
		System.out.println("Retailer added to GUI");
	}

	@Override
	public void onRetailerRemoved(AID retailerAID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTotalUsageForecastUpdated(double totalUsageForecast) {
		System.out.println("Total Usage updated: " + totalUsageForecast);
	}
}
