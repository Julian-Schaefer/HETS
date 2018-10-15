package org.cos30018.hets.ui.retailer;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.retailer.Retailer.NegotiationStrategy;
import org.cos30018.hets.logic.retailer.Retailer.PricingStrategy;
import org.cos30018.hets.ui.custom.AgentPanel.AgentPanelListener;
import org.cos30018.hets.ui.retailer.RetailerPanel.RetailerPanelListener;

import jade.core.AID;
import jade.wrapper.StaleProxyException;

public class RetailerPanelController implements RetailerPanelListener, AgentPanelListener {

	public RetailerPanelController(RetailerPanel retailerPanel) {
		retailerPanel.setRetailerPanelListener(this);
	}

	@Override
	public void onRetailerAddButtonClick() {
		JTextField retailerNameField = new JTextField();
		JComboBox<NegotiationStrategy> negotitationStrategyComboBox = new JComboBox<NegotiationStrategy>(
				NegotiationStrategy.values());
		JComboBox<PricingStrategy> pricingStrategyComboBox = new JComboBox<PricingStrategy>(PricingStrategy.values());

		Object[] retailerDialogComponents = { "Name", retailerNameField, "Negotiation Strategy", negotitationStrategyComboBox,
				"Pricing Strategy", pricingStrategyComboBox };

		JOptionPane.showConfirmDialog(null, retailerDialogComponents, "Add Retailer", JOptionPane.OK_CANCEL_OPTION);

		if (!retailerNameField.getText().isEmpty()) {
			try {
				JadeController.getInstance().addRetailerAgent(retailerNameField.getText(),
						(NegotiationStrategy) negotitationStrategyComboBox.getSelectedItem(),
						(PricingStrategy) pricingStrategyComboBox.getSelectedItem());
			} catch (StaleProxyException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Retailer name is empty.");
		}
	}

	@Override
	public void onShowDetailsClicked(AID aid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeleteClicked(AID aid) {
		JadeController.getInstance().removeAgent(aid);
	}

}
