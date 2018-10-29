package org.cos30018.hets.ui.home;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import jade.core.AID;
import jade.wrapper.StaleProxyException;
import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.retailer.Retailer;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.tariff.Tariff;
import org.cos30018.hets.ui.custom.ReadFile;
import org.cos30018.hets.ui.custom.WriteFile;
import org.cos30018.hets.ui.custom.button.StyledPopupMenuUI;
import org.cos30018.hets.ui.custom.button.StyledRoundButtonUI;
import org.cos30018.hets.ui.home.dashboard.HomeDashboardPanel;
import org.xml.sax.SAXException;

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
	private JButton btnConfigFile;

	private HomePanelController controller;

	private Home home;

	public HomePanel() {
		setLayout(homeLayout);
		this.home = JadeController.getInstance().getHome();
		this.controller = new HomePanelController(this);
		setUp();
	}

	private void setUp() {
		add(getHomeContentPanel(), HOME_SUB_PANEL);

		settingsPanel = new SettingsPanel(home);
		settingsPanel.setSettingsPanelListener(controller);
		add(settingsPanel, SETTINGS_PANEL);
	}

	public JPanel getHomeContentPanel() {
		JPanel homeContentLayout = new JPanel(new BorderLayout());
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel btnPanel = new JPanel(new BorderLayout());
		//btnPanel.setBackground(Color.WHITE);
		//titlePanel.setBackground(Color.white);
		titlePanel.setBorder(new EmptyBorder(20, 20, 5, 20));

		JLabel titleHome = new JLabel("Home");
		titleHome.setFont(new Font("Raleway", Font.BOLD, 40));


		btnConfigFile = new JButton();
		btnConfigFile.setIcon(new ImageIcon(getClass().getResource("/images/file_add_outline_2x_18dp.png")));
		btnConfigFile.setBackground(new Color(0x2dce98));
		btnConfigFile.setForeground(Color.white);
		btnConfigFile.setUI(new StyledRoundButtonUI());
		btnConfigFile.addActionListener((e) -> {
			showFileMenu(e);
		});

		btnSettings = new JButton();
		btnSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings_outline_2x_18dp.png")));
		btnSettings.setBackground(new Color(0x2dce98));
		btnSettings.setForeground(Color.white);
		btnSettings.setUI(new StyledRoundButtonUI());
		btnSettings.addActionListener((e) -> {
			showSettingsPanel();
		});

		titlePanel.add(titleHome, BorderLayout.CENTER);
		btnPanel.add(btnConfigFile, BorderLayout.WEST);
		btnPanel.add(btnSettings, BorderLayout.EAST);
		titlePanel.add(btnPanel, BorderLayout.EAST);
		homeContentLayout.add(titlePanel, BorderLayout.NORTH);

		HomeDashboardPanel homeDashboardPanel = new HomeDashboardPanel();
		homeDashboardPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
		JScrollPane scrollPane = new JScrollPane(homeDashboardPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		homeContentLayout.add(scrollPane, BorderLayout.CENTER);

		return homeContentLayout;
	}



	private void showFileMenu(ActionEvent e) {

		/**
		 * Creating Popup Menu
		 */
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItemSaveFile = new JMenuItem("Save Config File");
		menuItemSaveFile.setFont(new Font("Raleway", Font.PLAIN, 14));
		menuItemSaveFile.setBackground(Color.white);
		menuItemSaveFile.addActionListener((e1) -> {
			System.out.println("Save Config is clicked");
			SaveFile();

		});
		popupMenu.add(menuItemSaveFile);

		JMenuItem menuItemLoadFile = new JMenuItem("Load Config File");
		menuItemLoadFile.setFont(new Font("Raleway", Font.PLAIN, 14));
		menuItemLoadFile.setBackground(Color.white);
		menuItemLoadFile.addActionListener((e1) -> {
            System.out.println("Load Config is clicked");
            try {
                LoadFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (SAXException e2) {
                e2.printStackTrace();
            } catch (ParserConfigurationException e2) {
                e2.printStackTrace();
            } catch (StaleProxyException e2) {
                e2.printStackTrace();
            }

        });

		popupMenu.add(menuItemLoadFile);
		popupMenu.setUI(new StyledPopupMenuUI());




		/**
		 * Showing File menu
		 */
		//get the event Source
		Component c = (Component) e.getSource();

		// Get the location of the point 'on the screen'
		Point p = c.getLocationOnScreen();

		// Show the JPopupMenu via program

		// Parameter desc
		// ----------------
		// this - represents current frame
		// 0,0 is the co ordinate where the popup
		// is shown
		popupMenu.show(this,0,0);

		// Now set the location of the JPopupMenu
		// This location is relative to the screen
		popupMenu.setLocation(p.x,p.y+c.getHeight());



	}

    private void LoadFile() throws IOException, SAXException, ParserConfigurationException, StaleProxyException {

        ReadFile readFile = new ReadFile();

       List<List<String>> applianceList = readFile.LoadAppliances();

       for (List<String> appliance: applianceList) {

           System.out.println("name: " + appliance.get(0));
           System.out.println("type: " + appliance.get(1));
           System.out.println("forecast: " + appliance.get(2));

           String name = appliance.get(0);
           Appliance.ApplianceType type = Appliance.ApplianceType.valueOf(appliance.get(1));
           Appliance.ForecastingMethod forecastingMethod = Appliance.ForecastingMethod.valueOf(appliance.get(2));

           System.out.println("Appliance Type: " + type);
           System.out.println("Appliance Forecast method: " + forecastingMethod);

           JadeController.getInstance().addApplianceAgent(name, type, forecastingMethod);

       }

        List<List<String>> retailerList = readFile.LoadRetailers();

        for (List<String> retailer: retailerList) {

            System.out.println("name: " + retailer.get(0));
            System.out.println("strategy: " + retailer.get(1));
            System.out.println("tariff: " + retailer.get(2));


            String name = retailer.get(0);

            String tariff = retailer.get(2);


             // = Appliance.ApplianceType.valueOf(retailer.get(1));
           // Appliance.ForecastingMethod forecastingMethod = Appliance.ForecastingMethod.valueOf(retailer.get(2));

            //System.out.println("Appliance Type: " + strategy);
            System.out.println("Appliance Forecast method: " + tariff);
            //JadeController.getInstance().addRetailerAgent(name, Strategy.STRATEGY_FIXED_PRICE, Tariff.TARIFF_RANDOM);

        }

    }

    private void SaveFile() {

		WriteFile writeFile = new WriteFile();

		/** get Appliance List **/
		List<AID> aidApplianceList = JadeController.getInstance().getHome().getAppliances();
		List<Appliance> applianceList = new ArrayList<Appliance>();

		for (AID aid: aidApplianceList){
		    System.out.println(aid);
		    Appliance appliance = JadeController.getInstance().getAppliance(aid);
		    applianceList.add(appliance);

        }
        writeFile.writeAppliances(applianceList);

        System.out.println("APPLIANCES: " + applianceList);


        /** get Retailer List **/
        List<AID> aidRetailerList = JadeController.getInstance().getHome().getRetailers();
        List<Retailer> retailerList = new ArrayList<Retailer>();

        for (AID aid: aidRetailerList){
            System.out.println(aid);
            Retailer retailer = JadeController.getInstance().getRetailer(aid);
            retailerList.add(retailer);
        }
        writeFile.writeRetailers(retailerList);

        System.out.println("Retailers: " + retailerList);
	}



	public void showHomePanel() {
		homeLayout.previous(this);
	}

	public void showSettingsPanel() {
		homeLayout.next(this);
	}
}