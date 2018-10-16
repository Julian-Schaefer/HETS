package org.cos30018.hets.ui.home;

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.ui.custom.StyledButtonUI;
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

import net.miginfocom.swing.MigLayout;

public class HomePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3735404041724070782L;
	
	private static final String HOME_SUB_PANEL = "Home Sub Panel";
    private static final String SETTINGS_PANEL = "Settings Panel";

    private CardLayout homeLayout = new CardLayout();

    private SettingsPanel settingsPanel;
    private JButton btnSettings;
    
    private HomePanelController controller;


    public HomePanel() {
        setLayout(homeLayout);
        this.controller = new HomePanelController(this);
        setUp();
    }
    
    private void setUp() {
        add(HomeContentPanel(), HOME_SUB_PANEL);
        
        settingsPanel = new SettingsPanel();
        settingsPanel.setSettingsPanelListener(controller);
        add(settingsPanel, SETTINGS_PANEL);
    	
    }

    public JPanel HomeContentPanel() {

        /**
         * ui components for homeContent Layout
         */
        JPanel homeContentLayout = new JPanel(new MigLayout("insets 20 20 20 20"));
        homeContentLayout.setBackground(Color.white);

        JLabel titleHome = new JLabel("Home");
        titleHome.setFont(new Font("Raleway", Font.BOLD, 40));

        btnSettings = new JButton();
        btnSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings_outline_2x_18dp.png")));
        btnSettings.setBackground(new Color(0x2dce98));
        btnSettings.setForeground(Color.white);
        btnSettings.setUI(new StyledButtonUI());
        btnSettings.addActionListener((e) -> {
        	showSettingsPanel();
        });

        homeContentLayout.add(titleHome);
        homeContentLayout.add(btnSettings, "wrap 30");

        homeContentLayout.add(GraphChartPanel());

        return homeContentLayout;
    }

    private JPanel GraphChartPanel() {
        JPanel graphChartPanel = new JPanel(new MigLayout());
        graphChartPanel.setBackground(Color.WHITE);
        graphChartPanel.setPreferredSize(new Dimension(800, 600));

        /* Title of the chart */
        JLabel title = new JLabel("Energy Usage Actual vs Forecast");
        title.setFont(new Font("Raleway", Font.PLAIN, 20));

        graphChartPanel.add(title, "wrap");
        graphChartPanel.add(Graph());


        return graphChartPanel;
    }

    private JPanel Graph() {

        JPanel chartContent = new JPanel();
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(520, 400));

        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.WHITE);
        chartContent.add(chartPanel);

        return chartContent;
    }


    private XYDataset createDataset() {

        XYSeries series1 = new XYSeries("Actual");
        series1.add(18, 100);
        series1.add(20, 130);
        series1.add(25, 160);
        series1.add(30, 250);

        XYSeries series2 = new XYSeries("Forecast");
        series2.add(18, 130);
        series2.add(20, 160);
        series2.add(25, 120);
        series2.add(30, 300);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }


    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Forecast Energy usage",
                "Time",
                "Energy (Kwh)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer= new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle("Forecast Energy usage", new Font("Serif", Font.BOLD, 18)));


        return chart;
    }
    
    public void showHomePanel() {
        homeLayout.previous(this);    	
    }
    
    public void showSettingsPanel() {
        homeLayout.next(this);
    }
}