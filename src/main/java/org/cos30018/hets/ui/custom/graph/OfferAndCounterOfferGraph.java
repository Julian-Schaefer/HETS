package org.cos30018.hets.ui.custom.graph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.cos30018.hets.negotiation.Offer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class OfferAndCounterOfferGraph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1061945622069988748L;

	private Map<Integer, Offer> incomingOffers = new HashMap<>();
	private Map<Integer, Offer> outgoingOffers = new HashMap<>();

	private ChartPanel chartPanel;

	public OfferAndCounterOfferGraph() {
		setLayout(new BorderLayout());
		setUp();
	}

	private void setUp() {
		JFreeChart chart = createChart();
		chartPanel = new ChartPanel(chart);
		add(chartPanel, BorderLayout.CENTER);
	}

	public void setIncomingOffers(Map<Integer, Offer> incomingOffers) {
		this.incomingOffers = incomingOffers;
		chartPanel.setChart(createChart());
	}

	public void setOutgoingOffers(Map<Integer, Offer> outgoingOffers) {
		this.outgoingOffers = outgoingOffers;
	}

	public void update() {
		chartPanel.setChart(createChart());
	}

	private XYDataset createDataset() {
		XYSeries incoming = new XYSeries("Incoming Offer");
		for (Map.Entry<Integer, Offer> incomingOffer : incomingOffers.entrySet()) {
			incoming.add(incomingOffer.getKey(), (Double) incomingOffer.getValue().getPrice());
		}

		XYSeries outgoing = new XYSeries("Outgoing Offer");
		for (Map.Entry<Integer, Offer> outgoingOffer : outgoingOffers.entrySet()) {
			outgoing.add(outgoingOffer.getKey(), (Double) outgoingOffer.getValue().getPrice());
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(incoming);
		dataset.addSeries(outgoing);

		return dataset;
	}

	private JFreeChart createChart() {
		JFreeChart chart = ChartFactory.createXYLineChart("Price Negotiation", "Round", "Price/Kwh (Cent)",
				createDataset(), PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(false);
		plot.setDomainGridlinesVisible(false);

		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle("Price Negotiation", new Font("Serif", Font.BOLD, 18)));

		return chart;
	}
}
