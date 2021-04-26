package gg.salers.juaga.checks.player.protocol;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class ProtocolInvalidPitchCheck extends Check{

	public ProtocolInvalidPitchCheck() {
		super("Protocol [Invalid Pitch]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		 if(packet.getType() == PacketType.FLYING) {
			 if(jPlayer.getPlayer().getLocation().getPitch() > 90.0f || jPlayer.getPlayer().getLocation().getPitch() < -90.0f) {
				 fail(jPlayer);
			 }
		 }
	}

}
