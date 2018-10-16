package org.cos30018.hets.ui.home;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.StyledButtonUI;
import org.cos30018.hets.ui.home.dashboard.HomeDashboardPanel;

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

	private Home home;

	public HomePanel() {
		setLayout(homeLayout);
		this.home = JadeController.getInstance().getHome();
		this.controller = new HomePanelController(this);
		setUp();
	}

	private void setUp() {
		add(HomeContentPanel(), HOME_SUB_PANEL);

		settingsPanel = new SettingsPanel(home);
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

		JScrollPane scrollPane = new JScrollPane(new HomeDashboardPanel());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		homeContentLayout.add(scrollPane);

		return homeContentLayout;
	}

	public void showHomePanel() {
		homeLayout.previous(this);
	}

	public void showSettingsPanel() {
		homeLayout.next(this);
	}
}