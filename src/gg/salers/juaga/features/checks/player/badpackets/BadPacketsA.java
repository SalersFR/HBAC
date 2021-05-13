package gg.salers.juaga.features.checks.player.badpackets;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class BadPacketsA extends Check {

	public BadPacketsA() {
		super("BadPackets","A");
	}
	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getFrom() == null || jplayer.getFrom() == null)
			return;
		if(jPacket.getType() == PacketType.FLYING) {
			float pitch = Math.abs(jplayer.getTo().getPitch());
			if(pitch > 90F || pitch < -90F) {
				lagBack(jplayer);
				fail(jplayer , " pitch=" + (Math.abs(jplayer.getTo().getPitch())));
			}
		}
		
	}

}
