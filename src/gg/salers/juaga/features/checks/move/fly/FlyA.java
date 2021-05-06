package gg.salers.juaga.features.checks.move.fly;

import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.LocationUtils;

public class FlyA extends Check {

	private float threshold;

	public FlyA() {
		super("Fly", " A");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getFrom() == null || jplayer.getFrom() == null)
			return;
		if (jPacket.getType() == PacketType.POSITION) {
			if (jplayer.getTo().add(-0, -0.1, 0).getBlock().isLiquid()
					|| jplayer.getTo().add(0, 1, 0).getBlock().isLiquid()
					|| jplayer.getTo().add(0, 0, 0).getBlock().isLiquid()
					|| jplayer.getPlayer().getLocation().getBlock().isLiquid())
				return;
			jplayer.setDeltaY(jplayer.getTo().getY() - jplayer.getFrom().getY());
			if (jplayer.getFrom().add(0, -2, 0).getBlock().getType() == Material.AIR
					&& jplayer.getTo().add(0, -2, 0).getBlock().getType() == Material.AIR
					&& jplayer.getFrom().add(0, -1, 0).getBlock().getType() == Material.AIR // checking if player is &
																							// was in the air
					&& jplayer.getTo().add(0, -1, 0).getBlock().getType() == Material.AIR
					&& !LocationUtils.isCloseToGround(jplayer.getFrom())
					&& !LocationUtils.isCloseToGround(jplayer.getTo())) {
				double predictedDeltaY = (jplayer.getLastDeltaY() - 0.08) * 0.9800000190734863D; // from mc source code
				double result = jplayer.getDeltaY() - predictedDeltaY; // difference between the real distance and the
																		// predicted
				if (result > 67)
					return; // patch a false idk lol
				if (result > 0.1) { // due to some tests
					if (++threshold > 5) {
						fail(jplayer);

					}
				} else
					threshold *= 0.75; // for prevent falses

			}

		}

	}

}
