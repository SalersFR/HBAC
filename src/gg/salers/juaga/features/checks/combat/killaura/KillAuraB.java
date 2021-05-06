package gg.salers.juaga.features.checks.combat.killaura;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.JPacketUseAction;
import gg.salers.juaga.packets.PacketType;

public class KillAuraB extends Check {
	
	private int hits;

	 public KillAuraB() {
		super("KillAura","B");
	}
	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jPacket.getType() == PacketType.ARM_ANIMATION) {
			hits = 0;
		}else if(jPacket.getType() == PacketType.USE_ENTITY) {
			if(jplayer.getAction() == JPacketUseAction.ATTACK) {
				hits++;
				if(hits > 2) {
					fail(jplayer);
				}
			}
		}
		
	}

}
