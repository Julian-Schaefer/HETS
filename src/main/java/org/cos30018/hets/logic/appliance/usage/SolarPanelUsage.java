package org.cos30018.hets.logic.appliance.usage;

public class SolarPanelUsage implements ActualUsage {

	// area in square metres
	private static double AREA = 10;

	// efficiency of the solar panel
	private static double EFFICIENCY = 30;

	private double area;
	private double efficiency;
	private static final double WATTAGE_PER_SQ_METRE = 1000;

	public SolarPanelUsage() {
		this(AREA, EFFICIENCY);
	}

	public SolarPanelUsage(double area, double efficiency) {
		this.area = area;
		this.efficiency = efficiency;
	}

	@Override
	public double getActualUsage(int period) {
		int time = period % 24;

		if ((time >= 7) && (time <= 17)) {
			// solar panel returns a negative value
			return -1 * WATTAGE_PER_SQ_METRE / 1000 * area * efficiency;
		} else {
			return 0;
		}
	}
}
