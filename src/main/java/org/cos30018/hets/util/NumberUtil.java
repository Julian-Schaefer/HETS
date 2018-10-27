package org.cos30018.hets.util;

import java.text.DecimalFormat;

public class NumberUtil {

	public static String toString(double number) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(number);
	}

}
