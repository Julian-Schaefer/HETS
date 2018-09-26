package org.cos30018.hets.ui.appliance;

import javax.swing.JOptionPane;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.ui.appliance.AppliancePanel.AppliancePanelListener;

public class AppliancePanelController implements AppliancePanelListener {

	private AppliancePanel appliancePanel;
	
	public AppliancePanelController(AppliancePanel appliancePanel) {
		this.appliancePanel = appliancePanel;
		appliancePanel.setAppliancePanelListener(this);
	}

	@Override
	public void onApplianceAddButtonClick() {
		String name = JOptionPane.showInputDialog("Please enter the Appliance Name");
		JadeController.getInstance().addApplianceAgent(name);
		appliancePanel.addApplianceAgent(new ApplianceAgent());
	}

	@Override
	public void onShowAgentClicked(String name) {
		
	}
}
