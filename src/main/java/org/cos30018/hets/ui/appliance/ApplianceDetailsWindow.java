package org.cos30018.hets.ui.appliance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.ui.custom.ForecastAndActualGraph;

import jade.core.AID;

public class ApplianceDetailsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8967089543022762294L;

	private ForecastAndActualGraph usageGraph;
	private JLabel applianceTypeLabel;
	private JLabel forecastingMethodLabel;

	private Appliance appliance;

	public ApplianceDetailsWindow(AID aid) {
		super("Appliance: " + aid.getLocalName());
		this.appliance = JadeController.getInstance().getAppliance(aid);
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
		setUp();
		update();
		new ApplianceDetailsWindowController(this, appliance);
		setVisible(true);
	}

	private void setUp() {
		JPanel applianceInformationPanel = new JPanel(new GridLayout(2, 2));
		applianceInformationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		applianceInformationPanel.setBackground(Color.white);
		applianceInformationPanel.setPreferredSize(new Dimension(0, 110));

		JLabel applianceTypeTitleLabel = new JLabel("Appliance type:");
		applianceInformationPanel.add(applianceTypeTitleLabel);

		applianceTypeLabel = new JLabel();
		applianceInformationPanel.add(applianceTypeLabel);

		JLabel forecastingMethodTitleLabel = new JLabel("Forecasting method:");
		applianceInformationPanel.add(forecastingMethodTitleLabel);

		forecastingMethodLabel = new JLabel();
		applianceInformationPanel.add(forecastingMethodLabel);

		add(applianceInformationPanel, BorderLayout.NORTH);

		usageGraph = new ForecastAndActualGraph();
		add(usageGraph, BorderLayout.CENTER);
	}

	public void update() {
		applianceTypeLabel.setText(appliance.getType().name());
		forecastingMethodLabel.setText(appliance.getForecastingMethod().name());

		usageGraph.setActualValues(appliance.getActualUsages());
		usageGraph.setForecastValues(appliance.getUsageForecasts());
	}
}
