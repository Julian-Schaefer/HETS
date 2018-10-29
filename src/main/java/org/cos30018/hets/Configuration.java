package org.cos30018.hets;

import java.util.List;

import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.logic.retailer.Retailer;

public class Configuration {

	private Home home;
	private List<Appliance> appliances;
	private List<Retailer> retailers;

	public Configuration() {

	}

	public Configuration(Home home, List<Appliance> appliances, List<Retailer> retailers) {
		super();
		this.home = home;
		this.appliances = appliances;
		this.retailers = retailers;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public List<Appliance> getAppliances() {
		return appliances;
	}

	public void setAppliances(List<Appliance> appliances) {
		this.appliances = appliances;
	}

	public List<Retailer> getRetailers() {
		return retailers;
	}

	public void setRetailers(List<Retailer> retailers) {
		this.retailers = retailers;
	}

}
