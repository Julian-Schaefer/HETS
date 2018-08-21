package org.cos30018.hets;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class MyAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1227801713573723848L;

	@Override
	protected void setup() {
		System.out.println(getLocalName() + ": I have been created.");
		addBehaviour(new MyMultiStepBehaviour());
		addBehaviour(new MyOneShotBehaviour());
		addBehaviour(new MyCyclicBehaviour());
		System.out.println(getLocalName() + ": I have added my behaviours.");
	}
	
	@Override
	protected void takeDown() {
		System.out.println(getLocalName() + ": I am dying!");
	}
	
	private class MyCyclicBehaviour extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7008699430295881405L;

		public MyCyclicBehaviour() {
			System.out.println(getBehaviourName() + ": I have been created.");
		}
		
		@Override
		public void action() {
			System.out.println(getBehaviourName() + ": I bims am cyclen.");
		}
	}
	
	private class MyOneShotBehaviour extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3401979983520718026L;

		public MyOneShotBehaviour() {
			System.out.println(getBehaviourName() + ": I have been created.");
		}
		
		@Override
		public void action() {
			System.out.println(getBehaviourName() + ": I will be executed only once.");
		}
		
		@Override
		public int onEnd() {
			System.out.println(getBehaviourName() + ": Das wars auch schon wieder");
			return super.onEnd();
		}
		
	}
	
	private class MyMultiStepBehaviour extends Behaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1466380829938527585L;
		
		private int step = 1;
		
		public MyMultiStepBehaviour() {
			System.out.println("I have been created!");
		}
		
		@Override
		public void action() {
			switch(step) {
			case 1: System.out.println(getBehaviourName() + ": Operation 1."); break;
			case 2: System.out.println(getBehaviourName() + ": Operation 2."); break;
			case 3: System.out.println(getBehaviourName() + ": Operation 3."); break;
			case 4: System.out.println(getBehaviourName() + ": Operation 4."); break;
			}
			step++;
		}

		@Override
		public boolean done() {
			return step == 5;
		}
		
		@Override
		public int onEnd() {
			System.out.println(getBehaviourName() + ": I have finished executing.");
			System.out.println(getBehaviourName() + ": Jetzt finishe ich den Agent.");
			myAgent.doDelete();
			return super.onEnd();
		}
	}
	
}
