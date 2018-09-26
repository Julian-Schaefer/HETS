package org.cos30018.hets.logic;

import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.home.HomeAgent;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class JadeController {

	private static JadeController instance;
	
	private Runtime runtime = Runtime.instance();
	private AgentContainer mainContainer;
	
	private JadeControllerListener listener;
	
	public static JadeController getInstance() {
		if(instance == null) {
			instance = new JadeController();
		}
		
		return instance;
	}
	
	public void launchPlattform() {
		System.out.println(getClass().getSimpleName() + ": Launching the Main Platform container...");
		
		Profile pMain = new ProfileImpl(null, 8888, null);
		pMain.setParameter(Profile.GUI, "true");
		
		mainContainer = runtime.createMainContainer(pMain);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException interruptedException) {
			//Ignore
		}
		
        addAgent("homeAgent", HomeAgent.class);		
	}
	
	public void addApplianceAgent(String name) {
		addAgent(name, ApplianceAgent.class);
	}
	
	public void removeApplianceAgent() {
		
	}
	
	public void addRetailerAgent() {
		
	}
	
	public void removeRetailerAgent() {
		
	}
	
	public void setInterval() {
		
	}
    
    private void addAgent(String name, Class<? extends Agent> agentClass) {
		System.out.println(getClass().getSimpleName() + ": Starting up Agent: " + name);
		
		try {
			AgentController agentController = mainContainer.createNewAgent(name, agentClass.getName(), new Object[0]);
			agentController.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(300);
		} catch (InterruptedException interruptedException) {
			//Ignore
		}
		
		if(listener != null) listener.onApplianceAgentAdded(name);
    }
    
    public void setListener(JadeControllerListener listener) {
    	this.listener = listener;
    }
    
    public interface JadeControllerListener {
    	void onApplianceAgentAdded(String name);
    }
}
