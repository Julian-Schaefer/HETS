package org.cos30018.hets.ui.home.dashboard;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.ForecastAndActualGraph;
import org.cos30018.hets.ui.custom.StyledJPanelUI;

public class HomeDashboardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424522062931442939L;

	private Home home;

	private PeriodControllerPanel periodControllerPanel;
	private ForecastAndActualGraph forecastAndActualGraph;
	private InformationPanel informationPanel;

	public HomeDashboardPanel() {
		setLayout(new BorderLayout());
		//setBorder(new EmptyBorder(20, 20, 20, 20));
		this.home = JadeController.getInstance().getHome();
		new HomeDashboardPanelController(this, home);
		setUp();
	}

	private void setUp() {
		//setBorder(new EmptyBorder(20, 20, 50, 20));
		JPanel periodContainer = new JPanel(new BorderLayout());
		JPanel subContainer = new JPanel();
		subContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
		subContainer.setUI(new StyledJPanelUI());
		subContainer.setBackground(Color.WHITE);


		periodContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
		periodControllerPanel = new PeriodControllerPanel(home);
		subContainer.add(periodControllerPanel);

		periodContainer.add(subContainer);
		add(periodContainer, BorderLayout.NORTH);

		JPanel mainContainer = new JPanel(new BorderLayout());

		forecastAndActualGraph = new ForecastAndActualGraph();
		forecastAndActualGraph.setPreferredSize(new Dimension(620, 300));

		mainContainer.add(forecastAndActualGraph, BorderLayout.NORTH);

		informationPanel = new InformationPanel(home);
		informationPanel.setBorder(new EmptyBorder(50, 10, 10, 10));

		mainContainer.add(informationPanel, BorderLayout.CENTER);

		add(mainContainer, BorderLayout.CENTER);
	}

	public PeriodControllerPanel getPeriodControllerPanel() {
		return periodControllerPanel;
	}

	public ForecastAndActualGraph getForecastAndActualGraph() {
		return forecastAndActualGraph;
	}

	public InformationPanel getInformationPanel() {
		return informationPanel;
	}
}
