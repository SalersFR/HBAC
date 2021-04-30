package gg.salers.juaga.features.checks.combat.killaura;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class KillAuraA extends Check {

	private long lastFlying;
	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jPacket.getType() == PacketType.FLYING) {
			lastFlying = System.currentTimeMillis();
		}else if(jPacket.getType() == PacketType.USE_ENTITY) {
			long deltaFlying = System.currentTimeMillis() - lastFlying;
			if(jplayer.getPing() < 150) {
				if(deltaFlying < 25) {
					fail(jplayer, "KillAura (A)");
				}
			}
		}
	}

}
