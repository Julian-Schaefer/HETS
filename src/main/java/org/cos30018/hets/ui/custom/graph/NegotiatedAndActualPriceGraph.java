package org.cos30018.hets.ui.custom.graph;

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

public class NegotiatedAndActualPriceGraph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -94929123521678777L;

	private Map<Integer, Double> actualValues = new HashMap<>();
	private Map<Integer, Double> forecastValues = new HashMap<>();
	private Map<Integer, Double> differenceValues = new HashMap<>();

	private ChartPanel chartPanel;

	public NegotiatedAndActualPriceGraph() {
		setLayout(new BorderLayout());
		setUp();
	}

	private void setUp() {
		JFreeChart chart = createChart();
		chartPanel = new ChartPanel(chart);
		add(chartPanel, BorderLayout.CENTER);
	}

	public void addActualValue(int period, double actual) {
		actualValues.put(period, actual);
		chartPanel.setChart(createChart());
	}

	public void addForecastValue(int period, double forecast) {
		forecastValues.put(period, forecast);
		chartPanel.setChart(createChart());
	}

	public void addDifferenceValue(int period, double difference) {
		differenceValues.put(period, difference);
		chartPanel.setChart(createChart());
	}

	private XYDataset createDataset() {
		XYSeries actual = new XYSeries("Actual");
		for (Map.Entry<Integer, Double> actualValue : actualValues.entrySet()) {
			actual.add(actualValue.getKey(), actualValue.getValue());
		}

		XYSeries forecast = new XYSeries("Forecast");
		for (Map.Entry<Integer, Double> forecastValue : forecastValues.entrySet()) {
			forecast.add(forecastValue.getKey(), forecastValue.getValue());
		}

		XYSeries difference = new XYSeries("Difference");
		for (Map.Entry<Integer, Double> differenceValue : differenceValues.entrySet()) {
			difference.add(differenceValue.getKey(), differenceValue.getValue());
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(actual);
		dataset.addSeries(forecast);
		dataset.addSeries(difference);

		return dataset;
	}

	private JFreeChart createChart() {
		JFreeChart chart = ChartFactory.createXYLineChart("Actual vs. Negotiated Total Prices", "Time", "Price (Cents)",
				createDataset(), PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(1.0f));

		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesStroke(1, new BasicStroke(1.0f));

		renderer.setSeriesPaint(2, Color.GREEN);
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(false);
		plot.setDomainGridlinesVisible(false);

		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle("Actual vs. Negotiated Total Prices", new Font("Serif", Font.BOLD, 18)));

		return chart;
	}
}
