package org.cos30018.hets.ui.appliance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.ui.custom.graph.ForecastAndActualGraph;

import jade.core.AID;

public class ApplianceDetailsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8967089543022762294L;

	private ForecastAndActualGraph usageGraph;
	private JLabel applianceNameLabel;
	private JLabel applianceTypeLabel;
	private JLabel forecastingMethodLabel;

	private Appliance appliance;

	public ApplianceDetailsWindow(AID aid) {
		super("Appliance: " + aid.getLocalName());
		this.appliance = JadeController.getInstance().getAppliance(aid);
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 500));
		setLocationRelativeTo(null);
		setUp(aid);
		update();
		new ApplianceDetailsWindowController(this, appliance);
		setVisible(true);
	}

	private void setUp(AID aid) {
		JPanel applianceInformationPanel = new JPanel(new BorderLayout());
		applianceInformationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		applianceInformationPanel.setBackground(Color.WHITE);
		applianceInformationPanel.setPreferredSize(new Dimension(0, 150));

		JPanel infoContainer = new JPanel(new GridLayout(3, 2));
		infoContainer.setBackground(Color.WHITE);

		JLabel applianceNameTitleLabel = new JLabel("Name:");
		applianceNameTitleLabel.setFont(new Font("Raleway", Font.PLAIN, 20));
		applianceNameTitleLabel.setForeground((new Color(0x2dce98)));
		infoContainer.add(applianceNameTitleLabel);

		applianceNameLabel = new JLabel(aid.getLocalName());
		applianceNameLabel.setFont(new Font("Raleway", Font.BOLD, 24));
		applianceNameLabel.setForeground((new Color(0x2dce98)));
		infoContainer.add(applianceNameLabel);

		JLabel applianceTypeTitleLabel = new JLabel("Appliance type:");
		applianceTypeTitleLabel.setFont(new Font("Raleway", Font.PLAIN, 16));
		infoContainer.add(applianceTypeTitleLabel);

		applianceTypeLabel = new JLabel();
		applianceTypeLabel.setFont(new Font("Raleway", Font.PLAIN, 16));
		infoContainer.add(applianceTypeLabel);

		JLabel forecastingMethodTitleLabel = new JLabel("Forecasting method:");
		forecastingMethodTitleLabel.setFont(new Font("Raleway", Font.PLAIN, 16));
		infoContainer.add(forecastingMethodTitleLabel);

		forecastingMethodLabel = new JLabel();
		forecastingMethodLabel.setFont(new Font("Raleway", Font.PLAIN, 16));
		infoContainer.add(forecastingMethodLabel);

		applianceInformationPanel.add(infoContainer, BorderLayout.WEST);
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
