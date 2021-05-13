package gg.salers.juaga.features.checks.move.moveveloctiy;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.LocationUtils;

public class MoveVelocityC extends Check {

	
	private boolean wasWasCloseToGround, wasCloseToGround, isCloseToGround;

	public MoveVelocityC() {
		super("MoveVelocity", "C");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getFrom() == null || jplayer.getFrom() == null)
			return;
		if (jPacket.getType() == PacketType.POSITION) {

	}

	}
}
