package gg.salers.juaga.features.checks.move.fly;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class FlyC extends Check {

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jPacket.getType() == PacketType.ABILITIES) {
			boolean flying = jPacket.getPacketContainer().getBooleans().read(1);
			boolean allowFlight = jPacket.getPacketContainer().getBooleans().read(2);
			
			if(!allowFlight && flying) {
				lagBack(jplayer);
				fail(jplayer, "flying=" + flying + " allowFlight=" + allowFlight);
			}
		}
		
	}

}
