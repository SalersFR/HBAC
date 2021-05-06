package gg.salers.juaga.features.checks.player.ground;

import org.bukkit.Location;
import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.LocationUtils;

public class GroundA extends Check {

	// idea from Joshi
	private boolean wasWasCloseToGround, wasCloseToGround, isCloseToGround, isOnGround, wasOnGround;

	public GroundA() {
		super("Ground", "A");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getTo() == null || jplayer.getFrom() == null)
			return;
		if (jPacket.getType() == PacketType.POSITION) {
			isCloseToGround = LocationUtils.isCloseToGround(jplayer.getTo()); // cannot be spoofed (as i know)
			this.wasCloseToGround = isCloseToGround;
			this.wasWasCloseToGround = wasCloseToGround;
			isOnGround = jplayer.getPlayer().isOnGround(); // can be spoofed
			this.wasOnGround = isOnGround;
			if (!wasWasCloseToGround && !wasCloseToGround && !isCloseToGround
					&& jplayer.getPlayer().getLocation().add(0, -1.5, 0).getBlock().getType() == Material.AIR
					&& jplayer.getPlayer().getLocation().add(0, -0.999, 0).getBlock().getType() == Material.AIR 	&& jplayer.getPlayer().getLocation().add(0, -3, 0).getBlock().getType() == Material.AIR) {
				if (wasOnGround && isOnGround) {
					fail(jplayer);
				}

			}

		}

	}

}
