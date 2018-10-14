package org.cos30018.hets.ui.panels.Home;

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
                    System.out.println("Setting is Clicked");
                    layout.next(homePanel);

                } else if (e.getSource() == homePanel.btnHome) {
                    System.out.println("Home is Clicked");
                    layout.previous(homePanel);

                } else {
                    System.out.println("Nothing is Clicked");
                }


            }catch (RuntimeException ex) {
                System.out.println("Error occurred: " + ex);
            }

        }
    }
}
