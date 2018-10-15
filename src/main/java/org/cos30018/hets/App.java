package org.cos30018.hets;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.ui.HomeAgentController;

public class App 
{
    public static void main(String[] args)
    {
    	JadeController jadeController = JadeController.getInstance();
    	jadeController.launchPlattform();
    	new HomeAgentController(jadeController.getHome());
    }
}
