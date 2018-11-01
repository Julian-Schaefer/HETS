package org.cos30018.hets.negotiation.tariff;

import java.util.Map;

import org.cos30018.hets.util.DoubleRange;

public class BlockTariff extends Tariff {

	private Map<DoubleRange, DoubleRange> blockRates;

	public BlockTariff(Map<DoubleRange, DoubleRange> blockRates) {
		this.blockRates = blockRates;
	}

	@Override
	public double getVolumeCharge(double requestedAmount, int period) {
		for (Map.Entry<DoubleRange, DoubleRange> blockRate : blockRates.entrySet()) {
			DoubleRange range = blockRate.getKey();
			if (requestedAmount >= range.firstValue && requestedAmount <= range.secondValue) {
				return blockRate.getValue().firstValue;
			}
		}

		throw new NotInRangeException();
	}

	@Override
	public double getFeedInRate(double requestedAmount, int period) {
		for (Map.Entry<DoubleRange, DoubleRange> blockRate : blockRates.entrySet()) {
			DoubleRange range = blockRate.getKey();
			if (requestedAmount >= range.firstValue && requestedAmount <= range.secondValue) {
				return blockRate.getValue().secondValue;
			}
		}

		throw new NotInRangeException();
	}

	@Override
	public String getName() {
		return TARIFF_BLOCK;
	}

	public Map<DoubleRange, DoubleRange> getBlockRates() {
		return blockRates;
	}
}
