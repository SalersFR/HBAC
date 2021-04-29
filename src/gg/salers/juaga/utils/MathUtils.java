package gg.salers.juaga.utils;

import org.bukkit.util.Vector;

public class MathUtils {

	public static int msToTicks(final double time) {
		return (int) Math.round(time / 50.0);
	}
	// credits to Tecnio for the method above

	public static double getHorizontalDistanceToHitBox(Vector from, Vector to) {
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

}
