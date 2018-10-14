package org.cos30018.hets.ui.panels.Appliances;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppliancesController {
    
    private AppliancesPanel appliancesPanel;
    
    public AppliancesController(AppliancesPanel appliancesPanel) {
        this.appliancesPanel = appliancesPanel;
        this.appliancesPanel.addAppliancesPanelListener(new AppliancesPanelListener());
        
    }

    private class AppliancesPanelListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                if (e.getSource() == appliancesPanel.addBtn) {
                  System.out.println("Add Appliance Btn Clicked!");
                  appliancesPanel.AddApplianceDialogBox();
                }

            }catch (RuntimeException ex) {
                System.out.println("Error is occurred " + ex);
            }
            
        }
    }
}
