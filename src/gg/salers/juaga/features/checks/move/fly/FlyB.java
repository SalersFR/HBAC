package gg.salers.juaga.features.checks.move.fly;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class FlyB extends Check {

	public FlyB() {
		super("Fly"," B");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getFrom() == null || jplayer.getFrom() == null)
			return;
		if (jPacket.getType() == PacketType.POSITION) {
			if (jplayer.getFrom().add(0, -2, 0).getBlock().getType() == Material.AIR
					&& jplayer.getTo().add(0, -2, 0).getBlock().getType() == Material.AIR
					&& jplayer.getFrom().add(0, -1, 0).getBlock().getType() == Material.AIR // checking if player is and was in the air
					&& jplayer.getTo().add(0, -1, 0).getBlock().getType() == Material.AIR) {
				Vector vector = new Vector(jplayer.getTo().getX(), jplayer.getTo().getY(), jplayer.getTo().getZ());

				double distance = vector.distance(
						new Vector(jplayer.getFrom().getX(), jplayer.getFrom().getY(), jplayer.getFrom().getZ())); 
				if ((jplayer.getTo().getY() - jplayer.getFrom().getY()) > 0.1) //for patching falses while falling from very high
					return;
				
				
				if(jplayer.getTo().add(0,-3,0).getBlock().getType() != Material.AIR || jplayer.getPlayer().getFallDistance() != 0) return;

				
				if (!jplayer.getPlayer().isSprinting()) {

					if (distance > 1.23 && distance < 7) {
						lagBack(jplayer);
						fail(jplayer,"distance=" + distance + " sprinting=" + jplayer.getPlayer().isSprinting());

					}

				} else {

					if (distance > 1.6 && distance < 7) {
						lagBack(jplayer);
						fail(jplayer,"distance=" + distance + " sprinting=" + jplayer.getPlayer().isSprinting());

					}
				}
			}
		}

	}

}
