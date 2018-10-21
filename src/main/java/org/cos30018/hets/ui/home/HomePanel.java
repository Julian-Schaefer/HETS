package org.cos30018.hets.ui.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.StyledButtonUI;
import org.cos30018.hets.ui.home.dashboard.HomeDashboardPanel;

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
		titlePanel.setBackground(Color.white);
		titlePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
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

		titlePanel.add(titleHome, BorderLayout.CENTER);
		titlePanel.add(btnSettings, BorderLayout.EAST);
		homeContentLayout.add(titlePanel, BorderLayout.NORTH);

		HomeDashboardPanel homeDashboardPanel = new HomeDashboardPanel();
		homeDashboardPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
		JScrollPane scrollPane = new JScrollPane(homeDashboardPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		homeContentLayout.add(scrollPane, BorderLayout.CENTER);

		return homeContentLayout;
	}

	public void showHomePanel() {
		homeLayout.previous(this);
	}

	public void showSettingsPanel() {
		homeLayout.next(this);
	}
}