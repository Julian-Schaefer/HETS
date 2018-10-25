package org.cos30018.hets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.negotiation.utility.PriceUtility;
import org.cos30018.hets.ui.HomeAgentController;
import org.cos30018.hets.ui.custom.graph.NegotiatedPriceGraph;

public class App {
	public static void main(String[] args) {
		try {
			JadeController jadeController = JadeController.getInstance();
			jadeController.launchPlattform();
			new HomeAgentController(jadeController.getHome());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to start HETS. Maybe it is already running?", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		new TestWindow();
	}

	public static class TestWindow extends JFrame {

		private NegotiatedPriceGraph negotiatedPriceGraph;

		public TestWindow() {
			negotiatedPriceGraph = new NegotiatedPriceGraph();
			calculate();
			setSize(600, 600);
			add(negotiatedPriceGraph);
			setVisible(true);
		}

		private void calculate() {
			double deadline = 150.0;
			boolean increasing = true;

			for (double round = 1; round <= deadline; round++) {
				double kj = 0.0;
				double beta = 0.5;
				double alpha = kj + (1 - kj) * Math.pow((round / deadline), (1.0 / beta));

				double initialValue = 60.0;
				double reservationValue = 20.0;

				double minValue;
				double maxValue;

				maxValue = reservationValue;
				minValue = initialValue;

				double xJ;

				if (increasing) {
					xJ = minValue + alpha * (maxValue - minValue);
				} else {
					xJ = maxValue - alpha * (maxValue - minValue);
				}
				negotiatedPriceGraph.addNegotiatedPrice((int) round,
						new PriceUtility(30, 120, 0.1, 0.9).getUtility(round));
			}
		}
	}
}
