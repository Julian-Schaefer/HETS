package org.cos30018.hets.ui.home.dashboard;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.StyledJPanelUI;

public class InformationPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -6902463155733376886L;

    private Home home;

    JLabel forecastedTotalUsageLbl;
    JLabel lastActualTotalUsageLbl;
    JLabel registeredAppliancesLbl;
    JLabel registeredRetailersLbl;

    public InformationPanel(Home home) {
        this.home = home;
        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(500, 500));
        setUp();
        update();
    }

    private void setUp() {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);

        forecastedTotalUsageLbl = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 0;
        add(InfoCard("Forecasted total usage", forecastedTotalUsageLbl), gbc);

        lastActualTotalUsageLbl = new JLabel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(InfoCard("Last actual total usage", lastActualTotalUsageLbl), gbc);

        registeredAppliancesLbl = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 1;
        add(InfoCard("Registered Appliances", registeredAppliancesLbl), gbc);

        registeredRetailersLbl = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(InfoCard("Registered Appliances", registeredRetailersLbl), gbc);

    }


//    GridBagConstraints gbc = new GridBagConstraints();
//    gbc.weightx = 1;
//    gbc.weighty = 1;
//
//    JLabel forecastedTotalUsageTextLbl = new JLabel("Forecasted total usage: ");
//    gbc.gridx = 0;
//    gbc.gridy = 0;
//    add(forecastedTotalUsageTextLbl, gbc);
//
//    forecastedTotalUsageLbl = new JLabel();
//    gbc.gridx = 1;
//    gbc.gridy = 0;
//    add(forecastedTotalUsageLbl, gbc);
//
//    JLabel lastActualTotalUsageTextLbl = new JLabel("Last actual total usage: ");
//    gbc.gridx = 0;
//    gbc.gridy = 1;
//    add(lastActualTotalUsageTextLbl, gbc);
//
//    lastActualTotalUsageLbl = new JLabel();
//    gbc.gridx = 1;
//    gbc.gridy = 1;
//    add(lastActualTotalUsageLbl, gbc);
//
//    JLabel registeredAppliancesTextLbl = new JLabel("Registered Appliances: ");
//    gbc.gridx = 0;
//    gbc.gridy = 2;
//    add(registeredAppliancesTextLbl, gbc);
//
//    registeredAppliancesLbl = new JLabel();
//    gbc.gridx = 1;
//    gbc.gridy = 2;
//    add(registeredAppliancesLbl, gbc);
//
//    JLabel registeredRetailersTextLbl = new JLabel("Registered Retailers: ");
//    gbc.gridx = 0;
//    gbc.gridy = 3;
//    add(registeredRetailersTextLbl, gbc);
//
//    registeredRetailersLbl = new JLabel();
//    gbc.gridx = 1;
//    gbc.gridy = 3;
//    add(registeredRetailersLbl, gbc);
//}



    public JPanel InfoCard(String textLabel, JLabel valueLbl) {

        //setBackground(Color.WHITE);
        JPanel baseContainer = new JPanel(new BorderLayout());
        baseContainer.setBackground(Color.WHITE);
        baseContainer.setUI(new StyledJPanelUI());
        baseContainer.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);
        //container.setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel nameLbl = new JLabel(textLabel);
        nameLbl.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(nameLbl);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);

        valueLbl.setFont(new Font("Raleway", Font.BOLD, 18));
        bottom.add(valueLbl);

        baseContainer.add(container, BorderLayout.CENTER);
        baseContainer.add(bottom, BorderLayout.SOUTH);

        return baseContainer;
    }

    public void update() {

        forecastedTotalUsageLbl.setText(String.valueOf(home.getTotalUsageForecast()));
        lastActualTotalUsageLbl.setText(String.valueOf(home.getLastActualTotalUsage()));
        registeredAppliancesLbl.setText(String.valueOf(home.getAppliances().size()));
		registeredRetailersLbl.setText(String.valueOf(home.getRetailers().size()));
    }
}
