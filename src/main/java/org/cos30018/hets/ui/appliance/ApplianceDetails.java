package org.cos30018.hets.ui.appliance;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance;

import jade.core.AID;

public class ApplianceDetails extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8967089543022762294L;

	private Appliance appliance;
	
	public ApplianceDetails(AID aid) {
		super("Appliance: " + aid.getLocalName());
		this.appliance = JadeController.getInstance().getAppliance(aid);
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
		setup();
		setVisible(true);
	}
	
	private void setup() {
		JButton button = new JButton(appliance.getType().name());
		add(button);
	}
}
