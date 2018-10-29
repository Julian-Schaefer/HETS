package org.cos30018.hets;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.logic.appliance.Appliance.ForecastingMethod;
import org.junit.Before;
import org.junit.Test;

import jade.wrapper.StaleProxyException;

public class ApplianceTest {

	private JadeController jadeController;

	@Before
	public void setUp() {
		jadeController = new JadeController();
		jadeController.launchPlattform();
	}

	@Test
	public void testRegisterAppliance() throws StaleProxyException {
		jadeController.addApplianceAgent("test", ApplianceType.DISHWASHER, ForecastingMethod.SIMPLE, true);
	}
}
