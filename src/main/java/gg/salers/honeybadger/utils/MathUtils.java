package gg.salers.honeybadger.utils;

import com.comphenix.protocol.wrappers.Pair;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;


@UtilityClass
public class MathUtils {

    public final double EXPANDER = Math.pow(2, 24);


    public int floor(final double var0) {
        final int var2 = (int) var0;
        return var0 < var2 ? var2 - 1 : var2;
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

    public long getAbsoluteGcd(final float current, final float last) {

        final long currentExpanded = (long) (current * EXPANDER);

        final long lastExpanded = (long) (last * EXPANDER);

        return (long) getGcd(currentExpanded, lastExpanded);
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
    public long getGcd1(final long current, final long previous) {
        return (previous <= 16384L) ? current : getGcd1(previous, current % previous);
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

        double average;

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


    //CREDITS TO FREQUENCY

    /**
     * @param data - The set of numbers / data you want to find the standard deviation from.
     * @return - The standard deviation using the square root of the variance.
     * @See - https://en.wikipedia.org/wiki/Standard_deviation
     * @See - https://en.wikipedia.org/wiki/Variance
     */
    public double getStandardDeviation(final Collection<? extends Number> data) {
        final double variance = getVariance(data);

        // The standard deviation is the square root of variance. (sqrt(s^2))
        return Math.sqrt(variance);
    }

    /**
     * @param data - The set of numbers / data you want to find the skewness from
     * @return - The skewness running the standard skewness formula.
     * @See - https://en.wikipedia.org/wiki/Skewness
     */
    public double getSkewness(final Collection<? extends Number> data) {
        double sum = 0;
        int count = 0;

        final List<Double> numbers = Lists.newArrayList();

        // Get the sum of all the data and the amount via looping
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;

            numbers.add(number.doubleValue());
        }

        // Sort the numbers to run the calculations in the next part
        Collections.sort(numbers);

        // Run the formula to get skewness
        final double mean = sum / count;
        final double median = (count % 2 != 0) ? numbers.get(count / 2) : (numbers.get((count - 1) / 2) + numbers.get(count / 2)) / 2;
        final double variance = getVariance(data);

        return 3 * (mean - median) / variance;
    }


    public static int getDistinct(final Collection<? extends Number> collection) {
        Set<Object> set = new HashSet<>(collection);
        return set.size();
    }

    /**
     * @param - collection The collection of the numbers you want to get the duplicates from
     * @return - The duplicate amount
     */
    public static int getDuplicates(final Collection<? extends Number> collection) {
        return collection.size() - getDistinct(collection);
    }

    /**
     * @param - The collection of numbers you want analyze
     * @return - A pair of the high and low outliers
     * @See - https://en.wikipedia.org/wiki/Outlier
     */
    public Pair<List<Double>, List<Double>> getOutliers(final Collection<? extends Number> collection) {
        final List<Double> values = new ArrayList<>();

        for (final Number number : collection) {
            values.add(number.doubleValue());
        }

        final double q1 = getMedian(values.subList(0, values.size() / 2));
        final double q3 = getMedian(values.subList(values.size() / 2, values.size()));

        final double iqr = Math.abs(q1 - q3);
        final double lowbuffer = q1 - 1.5 * iqr, highbuffer = q3 + 1.5 * iqr;

        final Pair<List<Double>, List<Double>> tuple = new Pair<>(new ArrayList<>(), new ArrayList<>());

        for (final Double value : values) {
            if (value < lowbuffer) {
                tuple.getFirst().add(value);
            } else if (value > highbuffer) {
                tuple.getSecond().add(value);
            }
        }

        return tuple;
    }

    /**
     * @param data - The set of numbers/data you want to get the kurtosis from
     * @return - The kurtosis using the standard kurtosis formula
     * @See - https://en.wikipedia.org/wiki/Kurtosis
     */
    public double getKurtosis(final Collection<? extends Number> data) {
        double sum = 0.0;
        int count = 0;

        for (Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        if (count < 3.0) {
            return 0.0;
        }

        final double efficiencyFirst = count * (count + 1.0) / ((count - 1.0) * (count - 2.0) * (count - 3.0));
        final double efficiencySecond = 3.0 * Math.pow(count - 1.0, 2.0) / ((count - 2.0) * (count - 3.0));
        final double average = sum / count;

        double variance = 0.0;
        double varianceSquared = 0.0;

        for (final Number number : data) {
            variance += Math.pow(average - number.doubleValue(), 2.0);
            varianceSquared += Math.pow(average - number.doubleValue(), 4.0);
        }

        return efficiencyFirst * (varianceSquared / Math.pow(variance / sum, 2.0)) - efficiencySecond;
    }

    /**
     * @param data - The data you want the median from
     * @return - The middle number of that data
     * @See - https://en.wikipedia.org/wiki/Median
     */
    private double getMedian(final List<Double> data) {
        if (data.size() % 2 == 0) {
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        } else {
            return data.get(data.size() / 2);
        }
    }


    /**
     * @param from - The last location
     * @param to   - The current location
     * @return - The horizontal distance using (x^2 + z^2)
     */
    public double getMagnitude(final Location from, final Location to) {
        if (from.getWorld() != to.getWorld()) return 0.0;

        final Vector a = to.toVector();
        final Vector b = from.toVector();

        a.setY(0.0);
        b.setY(0.0);

        return a.subtract(b).length();
    }

    /**
     * @param player - The player you want to read the effect from
     * @param effect - The potion effect you want to get the amplifier of
     * @return - The amplifier added by one to make things more readable
     */
    public int getPotionLevel(final Player player, final PotionEffectType effect) {
        final int effectId = effect.getId();

        if (!player.hasPotionEffect(effect)) return 0;

        return player.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().getId() == effectId).map(PotionEffect::getAmplifier).findAny().orElse(0) + 1;
    }

    /**
     * @param data - The sample of clicks you want to get the cps from
     * @return - The cps using the average as a method of calculation
     */
    public double getCps(final Collection<? extends Number> data) {
        final double average = data.stream().mapToDouble(Number::doubleValue).average().orElse(0.0);

        return 20 / average;
    }
}
