package org.cos30018.hets.ui;

import org.cos30018.hets.ui.panels.Appliances.AppliancesController;
import org.cos30018.hets.ui.panels.Appliances.AppliancesPanel;
import org.cos30018.hets.ui.panels.Home.HomePanel;
import org.cos30018.hets.ui.panels.Home.HomePanelController;
import org.cos30018.hets.ui.panels.Retailers.RetailersPanel;
import org.cos30018.hets.ui.panels.Retailers.RetailersPanelController;

import javax.swing.*;
import java.awt.*;

public class HomeWindow extends JFrame {
    private static final String TAG = "HomeWindow";

    public HomeWindow(){
        super("HETS");
        System.out.println(TAG + " is created");
        setSize(1280, 600);

        setUp();
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void setUp() {

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());


        /**
         * Creating the 3 JPanels which holds as main contents for JFrame
         * @param AppliancesPanel:
         * @param HomePanel:
         * @param RetailersPanel:
         */
        AppliancesPanel appliancesPanel = new AppliancesPanel();
        HomePanel homePanel = new HomePanel();
        RetailersPanel retailerPanel = new RetailersPanel();

        JScrollPane leftScrollPane = new JScrollPane(appliancesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane rightScrollPane = new JScrollPane(retailerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScrollPane.setBorder(BorderFactory.createEmptyBorder());

        new HomePanelController(homePanel);
        new AppliancesController(appliancesPanel);
        new RetailersPanelController(retailerPanel);

        mainContent.add(leftScrollPane, BorderLayout.WEST);
        mainContent.add(homePanel, BorderLayout.CENTER);
        mainContent.add(rightScrollPane, BorderLayout.EAST);


        add(mainContent);
    }

}