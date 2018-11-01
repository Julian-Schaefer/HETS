package org.cos30018.hets.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.button.StyledRoundButtonUI;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1449215169896590692L;

	private SettingsPanelListener listener;

	private JButton homeButton;
	private JLabel registeredAppliancesLabel;
	private JLabel registeredRetailersLabel;

	private Home home;

	public SettingsPanel(Home home) {
		this.home = home;
		new SettingsPanelController(this, home);
		setUp();
		update();
	}

	private void setUp() {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.white);

		JLabel titleSettings = new JLabel("Settings");
		titleSettings.setFont(new Font("Raleway", Font.BOLD, 40));
		topPanel.add(titleSettings, BorderLayout.WEST);

		homeButton = new JButton();
		homeButton.setIcon(new ImageIcon(getClass().getResource("/images/home_outline_2x_18dp.png")));
		homeButton.setBackground(new Color(0x2dce98));
		homeButton.setForeground(Color.white);
		homeButton.setUI(new StyledRoundButtonUI());
		homeButton.addActionListener(homeButtonActionListener);

		topPanel.add(homeButton, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridLayout(4, 2));
		centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		centerPanel.setBackground(Color.white);

		JLabel buyingStrategyLbl = new JLabel("Buying Strategy");
		buyingStrategyLbl.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(buyingStrategyLbl));

		JButton buyingStrategyChangeButton = new JButton("Change Strategy");
		buyingStrategyChangeButton.addActionListener((e) -> {
			new HomeStrategySelectorWindow(home, false);
		});
		centerPanel.add(addToPanel(buyingStrategyChangeButton));

		JLabel sellingStrategyLbl = new JLabel("Selling Strategy");
		sellingStrategyLbl.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(sellingStrategyLbl));

		JButton sellingStrategyChangeButton = new JButton("Change Strategy");
		sellingStrategyChangeButton.addActionListener((e) -> {
			new HomeStrategySelectorWindow(home, true);
		});
		centerPanel.add(addToPanel(sellingStrategyChangeButton));

		JLabel lblRegisterApps = new JLabel("Currently Registered Appliances");
		lblRegisterApps.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(lblRegisterApps));

		registeredAppliancesLabel = new JLabel();
		centerPanel.add(addToPanel(registeredAppliancesLabel));

		JLabel lblRegisterRetailers = new JLabel("Currently Registered Retailers");
		lblRegisterRetailers.setFont(new Font("Raleway", Font.PLAIN, 16));
		centerPanel.add(addToPanel(lblRegisterRetailers));

		registeredRetailersLabel = new JLabel();
		centerPanel.add(addToPanel(registeredRetailersLabel));

		add(centerPanel, BorderLayout.CENTER);
	}

	private JPanel addToPanel(JComponent component) {
		JPanel panel = new JPanel();
		panel.add(component);
		return panel;
	}

	public void update() {
		registeredAppliancesLabel.setText(String.valueOf(home.getAppliances().size()));
		registeredRetailersLabel.setText(String.valueOf(home.getRetailers().size()));
	}

	public void setSettingsPanelListener(SettingsPanelListener listener) {
		this.listener = listener;
	}

	private ActionListener homeButtonActionListener = (e) -> {
		listener.onHomeButtonClicked();
	};

	public interface SettingsPanelListener {
		void onHomeButtonClicked();
	}
}
