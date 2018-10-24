package org.cos30018.hets.negotiation.tariff;

import java.util.Map;

public class BlockTariff extends Tariff {

	private Map<BlockRange, Double> blockRates;

	public BlockTariff(Map<BlockRange, Double> blockRates) {
		this.blockRates = blockRates;
	}

	@Override
	public double getPrice(double requestedAmount, int period) {
		for (Map.Entry<BlockRange, Double> blockRate : blockRates.entrySet()) {
			BlockRange range = blockRate.getKey();
			if (requestedAmount >= range.startCapacity && requestedAmount <= range.endCapacity) {
				return blockRate.getValue();
			}
		}

		throw new NotInRangeException();
	}

	@Override
	public String getName() {
		return TARIFF_BLOCK;
	}

	public static class BlockRange {
		public double startCapacity;
		public int endCapacity;
	}
}
