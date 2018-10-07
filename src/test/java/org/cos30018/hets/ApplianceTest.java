package org.cos30018.hets;

import org.cos30018.hets.logic.JadeController;
import org.junit.Before;
import org.junit.Test;

public class ApplianceTest {

	private JadeController jadeController;
	
	@Before
	public void setUp() {
		jadeController = new JadeController();
		jadeController.launchPlattform();
	}
	
	@Test
	public void testRegisterAppliance() {
		jadeController.addApplianceAgent("test");


	}
}
