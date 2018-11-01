package org.cos30018.hets.ui.appliance;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.logic.appliance.Appliance.ForecastingMethod;
import org.cos30018.hets.ui.appliance.AppliancePanel.AppliancePanelListener;
import org.cos30018.hets.ui.custom.AgentPanel.AgentPanelListener;

import jade.core.AID;
import jade.wrapper.StaleProxyException;

public class AppliancePanelController implements AppliancePanelListener, AgentPanelListener {

	public AppliancePanelController(AppliancePanel appliancePanel) {
		appliancePanel.setAppliancePanelListener(this);
	}

	@Override
	public void onApplianceAddButtonClick() {
		JTextField applianceNameField = new JTextField();
		JComboBox<ApplianceType> typeCombo = new JComboBox<ApplianceType>(ApplianceType.values());
		JComboBox<ForecastingMethod> forecastCombo = new JComboBox<ForecastingMethod>(ForecastingMethod.values());

		Object[] dialogComponents = { "Name", applianceNameField, "Type", typeCombo, "Forecast Method", forecastCombo };

		int result = JOptionPane.showConfirmDialog(null, dialogComponents, "Add Appliance",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			if (!applianceNameField.getText().isEmpty()) {
				try {
					JadeController.getInstance().addApplianceAgent(applianceNameField.getText(),
							(ApplianceType) typeCombo.getSelectedItem(),
							(ForecastingMethod) forecastCombo.getSelectedItem(), true);
				} catch (StaleProxyException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Appliance name is empty.");
			}
		}
	}

	@Override
	public void onShowDetailsClicked(AID aid) {
		new ApplianceDetailsWindow(aid);
	}

	@Override
	public void onDeleteClicked(AID aid) {
		JadeController.getInstance().removeAgent(aid);
	}
}
