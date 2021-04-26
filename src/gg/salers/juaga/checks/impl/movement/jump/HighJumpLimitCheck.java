package gg.salers.juaga.checks.impl.movement.jump;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.PlayerUtils;

public class HighJumpLimitCheck extends Check {

	private int preVL;

	public  HighJumpLimitCheck() {
		super("HighJump [YLimit]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if (packet.getType() == PacketType.FLYING) {
			if (!jPlayer.isExemptedMove()) {
				double maxJumpHeight = 0.41999998688697815;
				maxJumpHeight *= (PlayerUtils.getPotionEffectLevel(jPlayer.getPlayer(), PotionEffectType.JUMP) * 0.45);
				double distY = jPlayer.getTo().getY() - jPlayer.getFrom().getY();
				boolean isCloseToGround = PlayerUtils.isCloseToGround(jPlayer.getTo());
				if (!isCloseToGround) {

					Location b1 = jPlayer.getPlayer().getLocation().clone().add(0.3, -0.3, -0.3);
					Location b2 = jPlayer.getPlayer().getLocation().clone().add(-0.3, -0.3, -0.3);
					Location b3 = jPlayer.getPlayer().getLocation().clone().add(0.3, -0.3, 0.3);
					Location b4 = jPlayer.getPlayer().getLocation().clone().add(-0.3, -0.3, +0.3);

					if (b1.getBlock().getType() == Material.AIR && b2.getBlock().getType() == Material.AIR
							&& b3.getBlock().getType() == Material.AIR && b4.getBlock().getType() == Material.AIR) {
						if (distY > maxJumpHeight) {
							if (++preVL > 10) {
								fail(jPlayer);
							}
						}

					}
				}
			}

		}

	}
}
