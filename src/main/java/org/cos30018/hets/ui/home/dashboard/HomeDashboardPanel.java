package org.cos30018.hets.ui.home.dashboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.graph.ForecastAndActualGraph;
import org.cos30018.hets.ui.custom.graph.NegotiatedAndActualPriceGraph;
import org.cos30018.hets.ui.custom.graph.NegotiatedPriceGraph;

public class HomeDashboardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424522062931442939L;

	private Home home;

	private PeriodControllerPanel periodControllerPanel;
	private ForecastAndActualGraph forecastAndActualGraph;
	private NegotiatedPriceGraph negotiatedPriceGraph;
	private NegotiatedAndActualPriceGraph negotiatedAndActualPriceGraph;
	private ApplianceInformationPanel applianceInformationPanel;
	private RetailerInformationPanel retailerInformationPanel;

	public HomeDashboardPanel() {
		setLayout(new BorderLayout());
		this.home = JadeController.getInstance().getHome();
		new HomeDashboardPanelController(this, home);
		setUp();
	}

	private void setUp() {
		JPanel periodContainer = new JPanel(new BorderLayout());
		periodContainer.setBorder(new EmptyBorder(20, 0, 20, 0));
		periodControllerPanel = new PeriodControllerPanel(home);
		periodControllerPanel.setBorder(LineBorder.createGrayLineBorder());
		periodContainer.add(periodControllerPanel);
		add(periodContainer, BorderLayout.NORTH);

		JPanel mainContainer = new JPanel(new GridLayout(3, 2, 20, 20));
		mainContainer.setPreferredSize(new Dimension(0, 800));

		forecastAndActualGraph = new ForecastAndActualGraph();
		forecastAndActualGraph.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(forecastAndActualGraph);

		negotiatedPriceGraph = new NegotiatedPriceGraph();
		negotiatedPriceGraph.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(negotiatedPriceGraph);

		applianceInformationPanel = new ApplianceInformationPanel(home);
		applianceInformationPanel.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(applianceInformationPanel);

		retailerInformationPanel = new RetailerInformationPanel(home);
		retailerInformationPanel.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(retailerInformationPanel);

		negotiatedAndActualPriceGraph = new NegotiatedAndActualPriceGraph();
		negotiatedAndActualPriceGraph.setBorder(LineBorder.createGrayLineBorder());

		mainContainer.add(negotiatedAndActualPriceGraph);

		add(mainContainer, BorderLayout.CENTER);
	}

	public void update() {
		applianceInformationPanel.update();
		retailerInformationPanel.update();
	}

	public PeriodControllerPanel getPeriodControllerPanel() {
		return periodControllerPanel;
	}

	public ForecastAndActualGraph getForecastAndActualGraph() {
		return forecastAndActualGraph;
	}

	public NegotiatedPriceGraph getNegotiatedPriceGraph() {
		return negotiatedPriceGraph;
	}

	public NegotiatedAndActualPriceGraph getNegotiatedAndActualPriceGraph() {
		return negotiatedAndActualPriceGraph;
	}
}
