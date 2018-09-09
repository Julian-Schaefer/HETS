package org.cos30018.hets;

import org.cos30018.hets.appliance.ApplianceAgent;
import org.cos30018.hets.home.HomeAgent;
import org.cos30018.hets.retailer.RetailerAgent;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ui.HomeAgentController;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Runtime runtime = Runtime.instance();
	private static AgentContainer mainContainer;
	
    public static void main( String[] args )
    {
        HomeAgentController controller = new HomeAgentController();
        
        launchJade();
        addAgent("appliance1", ApplianceAgent.class);
        addAgent("retailer1", RetailerAgent.class);
        addAgent("homeAgent", HomeAgent.class);
    }
    
    private static void launchJade() {		
		System.out.println("MyJadeStarter Launching the Main Platform container...");
		
		Profile pMain = new ProfileImpl(null, 8888, null);
		pMain.setParameter(Profile.GUI, "true");
		
		mainContainer = runtime.createMainContainer(pMain);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException interruptedException) {
			//Ignore
		}
    }
    
    private static void addAgent(String name, Class<? extends Agent> agentClass) {
		System.out.println("MyJadeController: Starting up Agent: " + name);
		
		try {
			AgentController agentController = mainContainer.createNewAgent(name, agentClass.getName(), new Object[0]);
			agentController.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
    }
}
