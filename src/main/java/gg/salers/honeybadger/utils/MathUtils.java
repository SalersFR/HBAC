package gg.salers.honeybadger.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class MathUtils {

    /** Credits to Gaetan **/
    public int floor(final double var0) {
        final int var2 = (int) var0;
        return var0 < var2 ? var2 - 1 : var2;
    }

    public double getStandardDeviation(final Collection<? extends Number> data) {
        final double variance = getVariance(data);

        // The standard deviation is the square root of variance. (sqrt(s^2))
        return Math.sqrt(variance);
    }

      /*
    GladUrBad GCD & Sensitivity
    Medusa (by GladUrBad) - https://github.com/GladUrBad/Medusa/blob/f00848c2576e4812283e6dc2dc05e29e2ced866a/Impl/src/main/java/com/gladurbad/medusa/util/MathUtil.java
    Spigot Post - https://www.spigotmc.org/threads/determining-a-players-sensitivity.468373/
     */


    public double getGcd(final double a, final double b) {
        if (a < b) {
            return getGcd(b, a);
        }

        if (Math.abs(b) < 0.001) {
            return a;
        } else {
            return getGcd(b, a - Math.floor(a / b) * b);
        }
    }

    public double gcd(final double limit, final double a, final double b) {
        return b <= limit ? a : MathUtils.gcd(limit, b, a % b);
    }

    // Taken from https://github.com/ElevatedDev/Frequency

    /**
     * @param current  - The current value
     * @param previous - The previous value
     * @return - The GCD of those two values
     */
    public long getGcd(final long current, final long previous) {
        return (previous <= 16384L) ? current : getGcd(previous, current % previous);
    }

    public double getSensitivity(final float tDeltaPitch, final float tLastDeltaPitch) {
        final float deltaPitch = Math.abs(tDeltaPitch);
        final float lastDeltaPitch = Math.abs(tLastDeltaPitch);

        final float gcd = (float) MathUtils.getGcd(deltaPitch, lastDeltaPitch);
        final double sensMod = Math.cbrt(0.8333 * gcd);
        final double sensStepTwo = (1.666 * sensMod) - 0.3333;
        final double finalSens = sensStepTwo * 200;

        return finalSens;
    }


    public double getVariance(final Collection<? extends Number> data) {
        int count = 0;

        double sum = 0.0;
        double variance = 0.0;

        final double average;

        // Increase the sum and the count to find the average and the standard deviation
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        average = sum / count;

        // Run the standard deviation formula
        for (final Number number : data) {
            variance += Math.pow(number.doubleValue() - average, 2.0);
        }

        return variance;
    }
}
