package org.cos30018.hets.ui.appliance;

import javax.swing.JOptionPane;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.ui.appliance.AppliancePanel.AppliancePanelListener;

import jade.wrapper.StaleProxyException;

public class AppliancePanelController implements AppliancePanelListener {

	private AppliancePanel appliancePanel;
	
	public AppliancePanelController(AppliancePanel appliancePanel) {
		this.appliancePanel = appliancePanel;
		appliancePanel.setAppliancePanelListener(this);
	}

	@Override
	public void onApplianceAddButtonClick() {
		String name = JOptionPane.showInputDialog("Please enter the Appliance Name");
		try {
			JadeController.getInstance().addApplianceAgent(name, ApplianceType.DISHWASHER, ApplianceAgent.FORECASTING_SIMPLE);
		} catch (StaleProxyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void onShowAgentClicked(String name) {
		
	}
}
