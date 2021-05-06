package gg.salers.juaga.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class LocationUtils {

	public static List<JLocation> getEstimatedLocation(List<JLocation> location, int ping) {
		List<JLocation> locations = new ArrayList<>();
		int range = 0;
		double pingInTicks = MathUtils.msToTicks(ping);
		if (location.size() >= range + pingInTicks && !location.isEmpty()) {
			while (range < 5 ) {
				

				locations.add(location.get((int) (pingInTicks + range)));
				range++;

			}
			
		
		}
		return locations;
	}

	// from Jonhan, just improved a little tiny bit
	public static boolean isCloseToGround(Location location) {
		double distanceToGround = 0.3;
		for (double locX = -distanceToGround; locX <= distanceToGround; locX += distanceToGround) {
			for (double locZ = -distanceToGround; locZ <= distanceToGround; locZ += distanceToGround) {
				if (location.clone().add(0, -0.5001, 0).getBlock().getType() == Material.AIR) {
					return false;
				}
			}

		}
		return true;
	}
}
