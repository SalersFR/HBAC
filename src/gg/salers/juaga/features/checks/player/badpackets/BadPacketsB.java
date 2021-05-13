package gg.salers.juaga.features.checks.player.badpackets;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.JPacketUseAction;
import gg.salers.juaga.packets.PacketType;

public class BadPacketsB extends Check {
	public BadPacketsB() {
		super("BadPackets", "B");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jPacket.getType() == PacketType.USE_ENTITY) {
			if(jplayer.getAction() == JPacketUseAction.ATTACK) {
				if(jplayer.getAttacked() == jplayer.getPlayer()) {
					fail(jplayer,"");
				}
			}
		}

	}

}
