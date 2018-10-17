package org.cos30018.hets.ui.home.dashboard;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;

public class PeriodControllerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6425404148702431224L;

	private PeriodControllerPanelListener listener;

	private Home home;

	private JLabel currentPeriodLbl;

	public PeriodControllerPanel(Home home) {
		this.home = home;
		new PeriodControllerPanelController(this, home);
		setUp();
		update();
	}

	private void setUp() {
		JLabel currentPeriodTextLbl = new JLabel("Current Period: ");
		add(currentPeriodTextLbl);

		currentPeriodLbl = new JLabel();
		add(currentPeriodLbl);

		JButton nextPeriodButton = new JButton("Next period");
		nextPeriodButton.addActionListener((e) -> {
			listener.onNextPeriodButtonClicked();
		});
		add(nextPeriodButton);
	}

	public void update() {
		currentPeriodLbl.setText(String.valueOf(home.getPeriod()));
		updateUI();
	}

	public void setPeriodControllerPanelListener(PeriodControllerPanelListener listener) {
		this.listener = listener;
	}

	public interface PeriodControllerPanelListener {
		void onNextPeriodButtonClicked();
	}
}
