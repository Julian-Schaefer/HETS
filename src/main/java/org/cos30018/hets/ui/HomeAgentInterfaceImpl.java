package org.cos30018.hets.ui;

import java.util.ArrayList;
import java.util.List;

import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.retailer.RetailerAgent;

public class HomeAgentInterfaceImpl {

	private List<ApplianceAgent> appliances = new ArrayList<>();
	private List<RetailerAgent> retailers = new ArrayList<>();
	
	private HomeAgentListener listener;
	
	public void addAppliance(ApplianceAgent applianceAgent) {
		appliances.add(applianceAgent);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(listener != null) {
					listener.onApplianceAdded(applianceAgent);
				}
			}
		}).start();
	}
	
	public void removeAppliance(ApplianceAgent applianceAgent) {
		appliances.remove(applianceAgent);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(listener != null) {
					listener.onApplianceAdded(applianceAgent);
				}
			}
		}).start();
	}
	
	public void addRetailer(RetailerAgent retailerAgent) {
		retailers.add(retailerAgent);
				
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(listener != null) {
					listener.onRetailerAdded(retailerAgent);
				}
			}
		}).start();
	}
	
	public void removeRetailer(RetailerAgent retailerAgent) {
		retailers.remove(retailerAgent);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(listener != null) {
					listener.onRetailerAdded(retailerAgent);
				}
			}
		}).start();
	}
	
	public void setCheckInterval() {
		
	}
	
	public void setForecastPeriodCount() {
		
	}
	
	public void setListener(HomeAgentListener listener) {
		this.listener = listener;
	}
	
	
	public interface HomeAgentListener {
		void onApplianceAdded(ApplianceAgent appliance);
		void onApplianceRemoved(ApplianceAgent appliance);
		void onRetailerAdded(RetailerAgent retailer);
		void onRetailerRemoved(RetailerAgent retailer);
	}
	
}
