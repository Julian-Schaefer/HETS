package org.cos30018.hets.ui.retailer;

import org.cos30018.hets.logic.retailer.Retailer;
import org.cos30018.hets.logic.retailer.Retailer.RetailerListener;

public class RetailerDetailsWindowController implements RetailerListener {

	private RetailerDetailsWindow retailerDetailsWindow;

	public RetailerDetailsWindowController(RetailerDetailsWindow retailerDetailsWindow, Retailer retailer) {
		this.retailerDetailsWindow = retailerDetailsWindow;
		retailer.addRetailerListener(this);
	}

	@Override
	public void onNegotiationMessageAdded(String message) {
		retailerDetailsWindow.update();
	}
}
