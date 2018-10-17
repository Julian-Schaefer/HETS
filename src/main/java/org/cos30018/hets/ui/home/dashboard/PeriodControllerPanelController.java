package org.cos30018.hets.ui.home.dashboard;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.home.dashboard.PeriodControllerPanel.PeriodControllerPanelListener;

public class PeriodControllerPanelController implements PeriodControllerPanelListener {

	private PeriodControllerPanel periodControllerPanel;
	private Home home;

	public PeriodControllerPanelController(PeriodControllerPanel periodControllerPanel, Home home) {
		this.periodControllerPanel = periodControllerPanel;
		this.periodControllerPanel.setPeriodControllerPanelListener(this);
		this.home = home;
	}

	@Override
	public void onNextPeriodButtonClicked() {
		home.nextPeriod();
		periodControllerPanel.update();
	}
}
