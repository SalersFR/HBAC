package gg.salers.juaga.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerUtils {
	
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
	
	 public static Location getEyeLocation(Player player) {
	        Location eye = player.getLocation();
	        eye.setY(eye.getY() + player.getEyeHeight());
	        return eye;
	    }

}
