package gg.salers.juaga.features.checks.combat.killaura;

import org.bukkit.util.Vector;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class KillAuraC extends Check {

	public KillAuraC() {
		super("KillAura", "C");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jPacket.getType() == PacketType.USE_ENTITY) {
		
			 Vector victim = jplayer.getAttacked().getLocation().clone().toVector().setY(0.0).subtract(jplayer.getPlayer().getEyeLocation().clone().toVector().setY(0.0));
	         float angle = jplayer.getPlayer().getEyeLocation().getDirection().angle(victim);
	         float scaledAngle = angle * 100;
	         //(scaledAngle);
			if ((jplayer.getPlayer().getLocation().distance(jplayer.getAttacked().getLocation()) - 0.586) > 1.5) {
				if(angle > 1.0f) {
					
				}

			}
		}

	}

}
