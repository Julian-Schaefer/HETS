package org.cos30018.hets.ui;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.retailer.RetailerAgent;
import org.cos30018.hets.ui.HomeAgentInterface.HomeAgentListener;
import org.cos30018.hets.ui.appliance.AppliancePanelController;

public class HomeAgentController implements HomeAgentListener {

	private HomeAgentWindow view;
	
	private JadeController jadeController;
	private HomeAgentInterface homeAgentInterface;
	
	private AppliancePanelController appliancePanelController;
	
	public HomeAgentController(HomeAgentInterface homeAgentInterface) {
		setupAppliancePanel();
		
		this.homeAgentInterface = homeAgentInterface;
		
		homeAgentInterface.addAppliance(new ApplianceAgent());
	}
	
	private void setupAppliancePanel() {
		view = new HomeAgentWindow();
		appliancePanelController = new AppliancePanelController(view.getAppliancePanel());
		
	}

	@Override
	public void onApplianceAdded(ApplianceAgent appliance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onApplianceRemoved(ApplianceAgent appliance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRetailerAdded(RetailerAgent retailer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRetailerRemoved(RetailerAgent retailer) {
		// TODO Auto-generated method stub
		
	}
}
