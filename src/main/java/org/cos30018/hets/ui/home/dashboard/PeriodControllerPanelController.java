package org.cos30018.hets.ui.home.dashboard;

import java.util.concurrent.atomic.AtomicBoolean;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.home.Home.HomeListener;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.ui.home.dashboard.PeriodControllerPanel.PeriodControllerPanelListener;

import jade.core.AID;

public class PeriodControllerPanelController implements PeriodControllerPanelListener, HomeListener {

	private PeriodControllerPanel periodControllerPanel;
	private Home home;
	private SimulationThread simulationThread;

	public PeriodControllerPanelController(PeriodControllerPanel periodControllerPanel, Home home) {
		this.periodControllerPanel = periodControllerPanel;
		this.periodControllerPanel.setPeriodControllerPanelListener(this);
		this.home = home;
		this.home.addListener(this);
	}

	@Override
	public void onNextPeriodButtonClicked() {
		home.nextPeriod();
	}

	@Override
	public void onResetButtonClicked() {
		JadeController.getInstance().reset();
		periodControllerPanel.getHomePanel().reset();
	}

	@Override
	public void onStartSimulationClicked(double seconds) {
		if (simulationThread == null) {
			simulationThread = new SimulationThread(seconds);
			simulationThread.start();
		}
	}

	@Override
	public void onPauseSimulationClicked() {
		simulationThread.stopSimulation();
		simulationThread = null;
	}

	@Override
	public void onNewPeriod() {
		periodControllerPanel.update();
	}

	@Override
	public void onTotalUsageForecastUpdated(int period, double totalUsageForecast) {
	}

	@Override
	public void onActualTotalUsageUpdated(int period, double lastActualTotalUsage) {
	}

	@Override
	public void onNewNegotiatedOffer(int period, Offer offer) {
	}

	@Override
	public void onApplianceAdded(AID applianceAID) {
	}

	@Override
	public void onApplianceRemoved(AID applianceAID) {
	}

	@Override
	public void onRetailerAdded(AID retailerAID) {
	}

	@Override
	public void onRetailerRemoved(AID retailerAID) {
	}

	private class SimulationThread extends Thread {

		private final AtomicBoolean stopped = new AtomicBoolean(false);
		private double intervalSeconds;

		public SimulationThread(double intervalSeconds) {
			this.intervalSeconds = intervalSeconds;
		}

		public void stopSimulation() {
			stopped.set(true);
			interrupt();
		}

		@Override
		public void run() {
			while (!stopped.get()) {
				try {
					home.nextPeriod();
					Thread.sleep((long) (intervalSeconds * 1000.0));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}
		}
	}
}
