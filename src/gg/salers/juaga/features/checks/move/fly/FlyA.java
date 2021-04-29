package gg.salers.juaga.features.checks.move.fly;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class FlyA extends Check {

	private float threshold;

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jplayer.getFrom() == null || jplayer.getFrom() == null) return;
		if (jPacket.getType() == PacketType.POSITION) {
			if (jplayer.getFrom().add(0, -2, 0).getBlock().getType() == Material.AIR
					&& jplayer.getTo().add(0, -2, 0).getBlock().getType() == Material.AIR
					&& jplayer.getFrom().add(0, -1, 0).getBlock().getType() == Material.AIR
					&& jplayer.getTo().add(0, -1, 0).getBlock().getType() == Material.AIR) {
				double predictedDeltaY = (jplayer.getDeltaY() - 0.08) * 0.9800000190734863D;
				double result = jplayer.getDeltaY() - predictedDeltaY;

				if (result > 0.1) {
					if(++threshold > 5) {
						fail(jplayer, "Fly (A)");
					}
				}else threshold *= 0.75;

			}

		}

	}

}
