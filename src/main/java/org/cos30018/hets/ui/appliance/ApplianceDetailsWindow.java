package org.cos30018.hets.ui.appliance;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.ui.custom.ForecastAndActualGraph;

import jade.core.AID;

public class ApplianceDetailsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8967089543022762294L;

	private Appliance appliance;
	
	public ApplianceDetailsWindow(AID aid) {
		super("Appliance: " + aid.getLocalName());
		this.appliance = JadeController.getInstance().getAppliance(aid);
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
		setUp();
		setVisible(true);
	}
	
	private void setUp() {
		ForecastAndActualGraph graph = new ForecastAndActualGraph();
		add(graph);
		
		JButton add = new JButton("Add");
		add.addActionListener((e) -> {
			graph.update(Math.random()*100, Math.random()*100);;
		});
		add(add, BorderLayout.SOUTH);
	}
}
