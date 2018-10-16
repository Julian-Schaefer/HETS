package org.cos30018.hets.ui.home;

import org.cos30018.hets.ui.home.SettingsPanel.SettingsPanelListener;

public class HomePanelController implements SettingsPanelListener {

    private HomePanel homePanel;


    public HomePanelController(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

	@Override
	public void onHomeButtonClicked() {
		homePanel.showHomePanel();
	}
}
