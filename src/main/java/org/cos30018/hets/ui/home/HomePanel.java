package org.cos30018.hets.ui.home;

import net.miginfocom.swing.MigLayout;
import org.cos30018.hets.ui.custom.StyledButtonUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
        //implements ActionListener {

    private static final String HOME_SUB_PANEL = "Home Sub Panel";
    private static final String SETTINGS_PANEL = "Settings Panel";

    public CardLayout homeLayout = new CardLayout();
    JButton btnSettings;
    JButton btnHome;


    public HomePanel() {
        setLayout(homeLayout);

        add(HomeContentPanel(), HOME_SUB_PANEL);
        add(SettingsPanel(), SETTINGS_PANEL);

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

        homeContentLayout.add(titleHome);
        homeContentLayout.add(btnSettings, "wrap 30");

        JPanel content = new JPanel(new MigLayout("center"));
      //  content.setPreferredSize(new Dimension(1280, 800));


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



    public JPanel SettingsPanel(){

        /**
         * ui components for settingsContent Layout
         * @param titleSettings -> JLabel for title of the content
         * @param btnHome -> JButton to go to the Home Panel
         * @param settingsLayout -> JPanel main content which has all ui components for settings
         * @param content -> sub JPanel which has settings features
         */
        JPanel settingsLayout = new JPanel(new MigLayout("insets 20 20 20 20"));
        settingsLayout.setBackground(Color.white);

        JLabel titleSettings = new JLabel("Settings");
        titleSettings.setFont(new Font("Raleway", Font.BOLD, 40));

        JLabel subTitleGeneral = new JLabel("General");
        subTitleGeneral.setFont(new Font("Raleway", Font.BOLD, 24));

        btnHome = new JButton();
        btnHome.setIcon(new ImageIcon(getClass().getResource("/images/home_outline_2x_18dp.png")));
        btnHome.setBackground(new Color(0x2dce98));
        btnHome.setForeground(Color.white);
        btnHome.setUI(new StyledButtonUI());

        settingsLayout.add(titleSettings);
        settingsLayout.add(btnHome, "wrap 40");

        settingsLayout.add(subTitleGeneral,"wrap 20");




        JPanel content = new JPanel(new MigLayout("center"));
        content.setPreferredSize(new Dimension(1280, 800));
        content.setBackground(Color.white);

        JLabel subTitle = new JLabel("General");
        subTitle.setFont(new Font("Raleway", Font.PLAIN, 20));

        JLabel lblTimeInterval = new JLabel("Current Time Interval");
        lblTimeInterval.setFont(new Font("Raleway", Font.PLAIN, 16));

        JLabel lblForecast = new JLabel("Forecast for the next n periods");
        lblForecast.setFont(new Font("Raleway", Font.PLAIN, 16));

        JLabel lblRegisterApps = new JLabel("Currently Registered Appliances");
        lblRegisterApps.setFont(new Font("Raleway", Font.PLAIN, 16));

        JLabel lblRegisterRetailers = new JLabel("Currently Registered Retailers");
        lblRegisterRetailers.setFont(new Font("Raleway", Font.PLAIN, 16));

        JTextField tfTimeInterval = new JTextField("10 Seconds", 8);
        JTextField tfForecast = new JTextField("1", 8);

        JButton btnTimeChange = new JButton("Change");
        JButton btnForecastChange = new JButton("Change");

        btnTimeChange.setBackground(new Color(0x2dce98));
        btnTimeChange.setFont(new Font("Raleway", Font.BOLD, 14));
        btnTimeChange.setForeground(Color.white);
        btnTimeChange.setUI(new StyledButtonUI());

        btnForecastChange.setBackground(new Color(0x2dce98));
        btnForecastChange.setFont(new Font("Raleway", Font.BOLD, 14));
        btnForecastChange.setForeground(Color.white);
        btnForecastChange.setUI(new StyledButtonUI());

        JLabel valRegisteredApps = new JLabel("12");
        JLabel valRegisteredRetailers = new JLabel("5");

//        content.add(subTitle, "wrap");

        content.add(lblTimeInterval, "span 1");
        content.add(tfTimeInterval, "span 1");
        content.add(btnTimeChange, "span 3, wrap 5");

        content.add(lblForecast);
        content.add(tfForecast);
        content.add(btnForecastChange, "wrap 5");

        content.add(lblRegisterApps);
        content.add(valRegisteredApps, "wrap 5");

        content.add(lblRegisterRetailers);
        content.add(valRegisteredRetailers, "wrap");

        settingsLayout.add(content);


        return settingsLayout;
    }

    void addHomePanelListener(ActionListener listener) {
        btnSettings.addActionListener(listener);
        btnHome.addActionListener(listener);
    }
}