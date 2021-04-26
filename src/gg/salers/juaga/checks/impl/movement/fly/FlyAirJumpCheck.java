package gg.salers.juaga.checks.impl.movement.fly;

import org.bukkit.Material;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.PlayerUtils;

public class FlyAirJumpCheck extends Check {

	private boolean wasWasCloseToGround, wasCloseToGround, isCloseToGround;
	private int preVL;
	private double lastDist, dist;

	public  FlyAirJumpCheck() {
		super("Fly [Jump in mid Air]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if (packet.getType() == PacketType.FLYING) {
			if (!jPlayer.isExemptedMove()) {
				isCloseToGround = PlayerUtils.isCloseToGround(jPlayer.getTo());
				if (!wasWasCloseToGround && !wasCloseToGround && !isCloseToGround) {

					if (jPlayer.getPlayer().getNoDamageTicks() > 5) {
						dist = jPlayer.getTo().getY() - jPlayer.getFrom().getY();
						if(jPlayer.getFrom().clone().add(0,-1.999,0).getBlock().getType() == Material.AIR) {
							if(dist == 0.41999998688697815D && lastDist == 41999998688697815D) {
								fail(jPlayer);
								
							}
						}

					}
				}
				this.wasCloseToGround = isCloseToGround;
				this.wasWasCloseToGround = wasCloseToGround;
				this.lastDist = dist;

			}
		}

	}

}
