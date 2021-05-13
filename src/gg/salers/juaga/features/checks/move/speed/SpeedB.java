package gg.salers.juaga.features.checks.move.speed;

import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.LocationUtils;

public class SpeedB extends Check {

	private boolean wasOnGround, isOnGround;
	private float threshold;

	public SpeedB() {
		super("Speed", "B");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getFrom() == null || jplayer.getFrom() == null)
			return;
		if (jPacket.getType() == PacketType.POSITION) {
			isOnGround = jplayer.getPlayer().isOnGround();
			double limit = 0.7;
			this.isOnGround = jplayer.getPlayer().isOnGround();
			boolean wasOnGround = this.wasOnGround;
			this.wasOnGround = isOnGround;
			limit += jplayer.getPlayer().getWalkSpeed() > 0.2 ? 0 : jplayer.getPlayer().getWalkSpeed();
			if (jplayer.getPlayer().getLocation().clone().add(0, -0.999, 0).getBlock().getType() == Material.ICE
					|| jplayer.getPlayer().getLocation().clone().add(0, -0.999, 0).getBlock()
							.getType() == Material.PACKED_ICE) limit = 1.2;
				if (wasOnGround && isOnGround) {
					limit = 0.4;

				} else {
					limit = 0.623;
				}

			double deltaX = jplayer.getTo().getX() - jplayer.getFrom().getX();
			double deltaZ = jplayer.getTo().getZ() - jplayer.getFrom().getZ();
			double deltaXZ = Math.hypot(deltaX, deltaZ);
			if (deltaXZ > limit) {
				if (++threshold > 2) {
				  fail(jplayer, "deltaXZ=" + deltaXZ + " wasOnGround=" + wasOnGround + " isOnGround=" + isOnGround + " limit=" + limit);
				  lagBack(jplayer);
				}
			} else
				threshold *= 0.95;

		}

	}

}
