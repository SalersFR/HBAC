package gg.salers.juaga.utils;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MathUtils {
	
	/** Fields for some methods **/


	public static int msToTicks(final double time) {
		return (int) Math.round(time / 50.0);
	}
	// credits to Tecnio for the method above

	public static double getHorizontalDistanceToHitBox(Vector from, Vector to) {
		to.setY(0);
		from.setY(0);
		double nearestX = clamp(from.getX(), to.getX() - 0.4, to.getX() + 0.4);
		double nearestZ = clamp(from.getZ(), to.getZ() - 0.4, to.getZ() + 0.4);

		double distX = from.getX() - nearestX;
		double distZ = from.getZ() - nearestZ;

		return Math.hypot(distX, distZ);
	}

	public static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}
	//credits to Rowin
	
	
	 public static double getDeviation(final Collection<? extends Number> data) {
	        final double variance = getVariance(data);

	        // The standard deviation is the square root of variance. (sqrt(s^2))
	        return Math.sqrt(variance);
	    }

	    public static double getVariance(final Collection<? extends Number> data) {
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
	    
	    //from Gatean
	    
	   
	    public static Location getEyeLocation(Player player) {
	        Location eye = player.getLocation();
	        eye.setY(eye.getY() + player.getEyeHeight());
	        return eye;
	    }

}
