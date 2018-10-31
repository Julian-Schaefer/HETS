package org.cos30018.hets.ui.appliance;

import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.appliance.Appliance.ApplianceListener;

public class ApplianceDetailsWindowController implements ApplianceListener {

	private ApplianceDetailsWindow applianceDetailsWindow;

	public ApplianceDetailsWindowController(ApplianceDetailsWindow applianceDetailsWindow, Appliance appliance) {
		this.applianceDetailsWindow = applianceDetailsWindow;
		appliance.addListener(this);
	}

	@Override
	public void onNewActualUsage(int period, double usage) {
		applianceDetailsWindow.update();
	}

	@Override
	public void onNewUsageForecast(int period, double forecast) {
		applianceDetailsWindow.update();
	}

	@Override
	public void onReset() {
		applianceDetailsWindow.update();
	}
}
