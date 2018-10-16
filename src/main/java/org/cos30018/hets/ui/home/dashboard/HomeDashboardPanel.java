package org.cos30018.hets.ui.home.dashboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.ForecastAndActualGraph;

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
		this.home = JadeController.getInstance().getHome();
		new HomeDashboardPanelController(this, home);
		setUp();
	}

	private void setUp() {
		JPanel periodContainer = new JPanel(new BorderLayout());
		periodContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
		periodControllerPanel = new PeriodControllerPanel(home);
		periodControllerPanel.setBorder(LineBorder.createGrayLineBorder());
		periodContainer.add(periodControllerPanel);
		add(periodContainer, BorderLayout.NORTH);

		JPanel mainContainer = new JPanel(new GridLayout(2, 1, 20, 20));
		mainContainer.setPreferredSize(new Dimension(1000, 800));

		forecastAndActualGraph = new ForecastAndActualGraph();
		forecastAndActualGraph.setPreferredSize(new Dimension(960, 340));
		forecastAndActualGraph.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(forecastAndActualGraph);

		informationPanel = new InformationPanel(home);
		informationPanel.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(informationPanel);

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
