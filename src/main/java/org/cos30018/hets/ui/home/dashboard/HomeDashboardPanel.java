package org.cos30018.hets.ui.home.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledJPanelUI;
import org.cos30018.hets.ui.custom.graph.ForecastAndActualGraph;
import org.cos30018.hets.ui.custom.graph.NegotiatedAndActualPriceGraph;
import org.cos30018.hets.ui.custom.graph.NegotiatedPriceGraph;

public class HomeDashboardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424522062931442939L;

	private Home home;

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
		JPanel mainContainer = new JPanel(new GridLayout(3, 2, 20, 20));
		mainContainer.setUI(new StyledJPanelUI());
		mainContainer.setBackground(Color.WHITE);
		mainContainer.setPreferredSize(new Dimension(0, 1200));

		forecastAndActualGraph = new ForecastAndActualGraph();

		mainContainer.add(forecastAndActualGraph);

		negotiatedPriceGraph = new NegotiatedPriceGraph();

		mainContainer.add(negotiatedPriceGraph);

		applianceInformationPanel = new ApplianceInformationPanel(home);

		mainContainer.add(applianceInformationPanel);

		retailerInformationPanel = new RetailerInformationPanel(home);

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
