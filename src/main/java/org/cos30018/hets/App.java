package org.cos30018.hets;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Runtime runtime = Runtime.instance();
        		
		System.out.println("MyJadeStarter Launching the Main Platform container...");
		
		Profile pMain = new ProfileImpl(null, 8888, null);
		pMain.setParameter(Profile.GUI, "true");
		
		AgentContainer mainCtrl = runtime.createMainContainer(pMain);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException interruptedException) {
			//Ignore
		}
		
		System.out.println("MyJadeController: Starting up a CounterAgent...");
		AgentController agentCtrl;
		
		try {
			agentCtrl = mainCtrl.createNewAgent("CounterAgent", MyAgent.class.getName(), new Object[0]);
    		agentCtrl.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
    }
}
