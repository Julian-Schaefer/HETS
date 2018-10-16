package org.cos30018.hets.ui.home;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanelController {

    private HomePanel homePanel;


    public HomePanelController(HomePanel homePanel) {
        this.homePanel = homePanel;
        this.homePanel.addHomePanelListener(new HomePanelListener());
    }

    private class HomePanelListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            /**
             * Action event for btn Listeners in HomePanel
             * @param layout: Main layout used to switch between home and settings panels
             * @param homePanel: Reference to Main class which holds panels
             */
            CardLayout layout = (CardLayout) homePanel.getLayout();

            try{
                if (e.getSource() == homePanel.btnSettings){
                    layout.next(homePanel);
                } else if (e.getSource() == homePanel.btnHome) {
                    layout.previous(homePanel);
                }
            }catch (RuntimeException ex) {
                System.out.println("Error occurred: " + ex);
            }

        }
    }
}
