package org.cos30018.hets.ui;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.JadeController.JadeControllerListener;
import org.cos30018.hets.ui.appliance.AppliancePanelController;

public class HomeAgentController implements JadeControllerListener {

	private HomeAgentWindow view;
	
	private JadeController jadeController;
	
	private AppliancePanelController appliancePanelController;
	
	public HomeAgentController() {
		JadeController jadeController = new JadeController();
		jadeController.setListener(this);
		jadeController.launchPlattform();
		
		setupAppliancePanel();
	}
	
	private void setupAppliancePanel() {
		view = new HomeAgentWindow();
		appliancePanelController = new AppliancePanelController(view.getAppliancePanel());
		
	}

	@Override
	public void onApplianceAgentAdded(String name) {
	}
}
