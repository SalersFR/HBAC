package gg.salers.juaga.checks.player.protocol;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class ProtocolSelfHitCheck extends Check {

	public  ProtocolSelfHitCheck() {
		super("Protocol [Self Hit]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if(packet.getType() == PacketType.USE_ENTITY) {
			if(jPlayer.getLastTarget() == jPlayer.getPlayer()) {
				fail(jPlayer);
			}
		}
		
	}

}
