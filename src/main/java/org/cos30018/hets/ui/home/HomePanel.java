package org.cos30018.hets.ui.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.Configuration;
import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.retailer.Retailer;
import org.cos30018.hets.ui.custom.button.StyledPopupMenuUI;
import org.cos30018.hets.ui.custom.button.StyledRoundButtonUI;
import org.cos30018.hets.ui.home.dashboard.HomeDashboardPanel;
import org.cos30018.hets.util.ConfigurationReader;
import org.cos30018.hets.util.ConfigurationWriter;

import jade.core.AID;

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
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItemSaveFile = new JMenuItem("Save Config File");
		menuItemSaveFile.setFont(new Font("Raleway", Font.PLAIN, 14));
		menuItemSaveFile.setBackground(Color.white);
		menuItemSaveFile.addActionListener((e1) -> {
			SaveFile();
		});
		popupMenu.add(menuItemSaveFile);

		JMenuItem menuItemLoadFile = new JMenuItem("Load Config File");
		menuItemLoadFile.setFont(new Font("Raleway", Font.PLAIN, 14));
		menuItemLoadFile.setBackground(Color.white);
		menuItemLoadFile.addActionListener((e1) -> {
			LoadFile();
		});

		popupMenu.add(menuItemLoadFile);
		popupMenu.setUI(new StyledPopupMenuUI());

		Component c = (Component) e.getSource();
		Point p = c.getLocationOnScreen();

		// Show the JPopupMenu via program

		// Parameter desc
		// ----------------
		// this - represents current frame
		// 0,0 is the co ordinate where the popup
		// is shown
		popupMenu.show(this, 0, 0);

		// Now set the location of the JPopupMenu
		// This location is relative to the screen
		popupMenu.setLocation(p.x, p.y + c.getHeight());
	}

	public void showHomePanel() {
		homeLayout.previous(this);
	}

	public void showSettingsPanel() {
		homeLayout.next(this);
	}

	private void LoadFile() {
		ConfigurationReader.readConfiguration();
	}

	private void SaveFile() {
		List<AID> aidApplianceList = JadeController.getInstance().getHome().getAppliances();
		List<Appliance> applianceList = new ArrayList<Appliance>();

		for (AID aid : aidApplianceList) {
			Appliance appliance = JadeController.getInstance().getAppliance(aid);
			applianceList.add(appliance);

		}

		List<AID> aidRetailerList = JadeController.getInstance().getHome().getRetailers();
		List<Retailer> retailerList = new ArrayList<Retailer>();

		for (AID aid : aidRetailerList) {
			Retailer retailer = JadeController.getInstance().getRetailer(aid);
			retailerList.add(retailer);
		}

		Configuration config = new Configuration(JadeController.getInstance().getHome(), applianceList, retailerList);
		ConfigurationWriter.writeConfig(config);
	}
}