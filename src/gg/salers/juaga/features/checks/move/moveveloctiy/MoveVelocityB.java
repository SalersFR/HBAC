package gg.salers.juaga.features.checks.move.moveveloctiy;

import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.LocationUtils;

public class MoveVelocityB extends Check {

	private boolean wasOnGround, isOnGround;

	public MoveVelocityB() {
		super("MoveVelocity", "B");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jplayer.getTo() == null || jplayer.getFrom() == null) return;
		if( jplayer.getPlayer().getFallDistance() != 0) return;
		if (jPacket.getType() == PacketType.POSITION) {
			isOnGround = jplayer.getPlayer().isOnGround();
			if (wasOnGround && isOnGround) {
				double motionY = jplayer.getPlayer().getVelocity().getY();

				if (jplayer.getTo().add(-0, -0.1, 0).getBlock().isLiquid()
						|| jplayer.getTo().add(0, 1, 0).getBlock().isLiquid()
						|| jplayer.getTo().add(0, 0, 0).getBlock().isLiquid()
						|| jplayer.getPlayer().getLocation().getBlock().isLiquid()
					    || jplayer.getTo().add(0,-0.1699,0).getBlock().getType() == Material.AIR
					    || jplayer.getFrom().add(0,-0.1699,0).getBlock().getType() == Material.AIR)
					return;
				if (motionY != -0.0784000015258789) { //motionY should be -0.0784000015258789
					fail(jplayer);
				}
			} else {
				double motionY = jplayer.getPlayer().getVelocity().getY();
				if (jplayer.getTo().add(-0, -0.1, 0).getBlock().isLiquid()
						|| jplayer.getTo().add(0, 1, 0).getBlock().isLiquid()
						|| jplayer.getTo().add(0, 0, 0).getBlock().isLiquid()
						|| jplayer.getPlayer().getLocation().getBlock().isLiquid()
					    && jplayer.getPlayer().getLocation().add(0,-0.11,0).getBlock().getType() == Material.AIR)
					return;
				if (!LocationUtils.isCloseToGround(jplayer.getFrom())
						&& !LocationUtils.isCloseToGround(jplayer.getTo())) {
					if (motionY == -0.0784000015258789) { //motionY should not be  -0.0784000015258789
						fail(jplayer);
					}
				}
				this.wasOnGround = isOnGround;

			}

		}
	}

}
