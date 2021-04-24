package gg.salers.juaga.utils;

import java.util.Collection;

public class MathUtils {


	public static double getVariance(final Collection<? extends Number> data) {
		int count = 0;

		double sum = 0.0;
		double variance = 0.0;

		double average;

		for (final Number number : data) {
			sum += number.doubleValue();
			++count;
		}

		average = sum / count;

		for (final Number number : data) {
			variance += Math.pow(number.doubleValue() - average, 2.0);
		}

		return variance;
	}

	public static double getStandardDeviation(final Collection<? extends Number> data) {
		final double variance = getVariance(data);

		return Math.sqrt(variance);
	}
	//Credits to Medusa for the 2 methods above
}
