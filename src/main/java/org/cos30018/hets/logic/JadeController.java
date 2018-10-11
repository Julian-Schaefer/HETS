package org.cos30018.hets.logic;

import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.logic.appliance.Appliance.ForecastingMethod;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.HomeAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class JadeController {

	private static JadeController instance;
	
	private Runtime runtime = Runtime.instance();
	private AgentContainer mainContainer;
	
	private Home home;
	
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
		
		setupHomeAgent();
	}
	
	private void setupHomeAgent() {
		try {
			AgentController homeAgentController = addAgent("homeAgent", HomeAgent.class);
			this.home = homeAgentController.getO2AInterface(Home.class);
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}		
	}
	
	public Home getHome() {
		return home;
	}
	
	public void addApplianceAgent(String name, ApplianceType applianceType, ForecastingMethod forecastingMethod) throws StaleProxyException {
		addAgent("appliance_" + name, ApplianceAgent.class, new Object[] { applianceType, forecastingMethod });
	}
	
	public Appliance getAppliance(AID aid) {
		try {
			AgentController agentController = mainContainer.getAgent(aid.getName(), true);
			return agentController.getO2AInterface(Appliance.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addRetailerAgent() {
	}
	
	public void removeAgent(AID aid) {
		try {
			AgentController agentController = mainContainer.getAgent(aid.getName(), true);
			agentController.kill();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setInterval() {
		
	}

    private AgentController addAgent(String name, Class<? extends Agent> agentClass) throws StaleProxyException {
    	return addAgent(name, agentClass, new Object[0]);
    }
    
    private AgentController addAgent(String name, Class<? extends Agent> agentClass, Object[] arguments) throws StaleProxyException {
		System.out.println(getClass().getSimpleName() + ": Starting up Agent: " + name);
		
		AgentController agentController = mainContainer.createNewAgent(name, agentClass.getName(), arguments);
		agentController.start();
		
		return agentController;
    }
}
