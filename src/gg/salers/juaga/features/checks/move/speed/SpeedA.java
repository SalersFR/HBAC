package gg.salers.juaga.features.checks.move.speed;

import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class SpeedA extends Check {

	private double deltaXZ, pastDeltaXZ;

	public SpeedA() {
		super("Speed ","A");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jplayer.getTo() == null || jplayer.getFrom() == null) return;
		if (jPacket.getType() == PacketType.POSITION) {
			if (jplayer.getTo().add(0, -0.5, 0).getBlock().getType() == Material.ICE
					|| jplayer.getTo().add(0, -0.5, 0).getBlock().getType() == Material.PACKED_ICE)
				return;

			double deltaX = jplayer.getTo().getX() - jplayer.getFrom().getX();
			double deltaZ = jplayer.getTo().getZ() - jplayer.getFrom().getZ();
			deltaXZ = Math.hypot(deltaX, deltaZ);
			double pastDeltaXZ = this.pastDeltaXZ;
			this.pastDeltaXZ = deltaXZ;
			double acceleration = deltaXZ - pastDeltaXZ;

			if (acceleration > 0.65) {
				fail(jplayer ," deltaXZ=" + deltaXZ + " pastDeltaXZ=" + pastDeltaXZ + " acceleration=" + acceleration);
				lagBack(jplayer);

			}

		}
		
	}

}
