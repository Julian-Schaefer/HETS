package org.cos30018.hets.ui.retailer;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.ui.custom.AgentPanel.AgentPanelListener;
import org.cos30018.hets.ui.retailer.RetailerPanel.RetailerPanelListener;

import jade.core.AID;

public class RetailerPanelController implements RetailerPanelListener, AgentPanelListener {

	public RetailerPanelController(RetailerPanel retailerPanel) {
		retailerPanel.setRetailerPanelListener(this);
	}

	@Override
	public void onRetailerAddButtonClick() {
		new AddRetailerWindow();
	}

	@Override
	public void onShowDetailsClicked(AID aid) {
		new RetailerDetailsWindow(aid);
	}

	@Override
	public void onDeleteClicked(AID aid) {
		JadeController.getInstance().removeAgent(aid);
	}

}
