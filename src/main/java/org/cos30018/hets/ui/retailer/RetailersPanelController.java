package org.cos30018.hets.ui.retailer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RetailersPanelController {

    private RetailersPanel retailersPanel;

    public RetailersPanelController(RetailersPanel retailersPanel){
        this.retailersPanel = retailersPanel;
        this.retailersPanel.addRetailersPanelListener(new RetailersPanelListener());
    }

    private class RetailersPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                if (e.getSource() == retailersPanel.addRetailerBtn) {
                    System.out.println("Add Retailer Btn is Clicked!");
                    retailersPanel.AddRetailersDialogBox();
                }

            }catch (RuntimeException ex) {

            }

        }
    }
}
