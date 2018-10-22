package org.cos30018.hets.ui.custom;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

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

public class NegotiatedPriceGraph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8072911533849731056L;

	private Map<Integer, Double> negotiatedPrices = new HashMap<>();

	private ChartPanel chartPanel;

	public NegotiatedPriceGraph() {
		setLayout(new BorderLayout());
		setUp();
	}

	private void setUp() {
		JFreeChart chart = createChart();
		chartPanel = new ChartPanel(chart);
		add(chartPanel, BorderLayout.CENTER);
	}

	public void addNegotiatedPrice(int period, double price) {
		negotiatedPrices.put(period, price);
		chartPanel.setChart(createChart());
	}

	private XYDataset createDataset() {

		XYSeries negotiated = new XYSeries("Negotiated Price");
		for (Map.Entry<Integer, Double> negotiatedPrice : negotiatedPrices.entrySet()) {
			negotiated.add(negotiatedPrice.getKey(), negotiatedPrice.getValue());
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(negotiated);

		return dataset;
	}

	private JFreeChart createChart() {
		JFreeChart chart = ChartFactory.createXYLineChart("Negotiated Energy prices", "Period", "Price/Kwh (Cent)",
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
		chart.setTitle(new TextTitle("Negotiated Energy prices", new Font("Serif", Font.BOLD, 18)));

		return chart;
	}
}
