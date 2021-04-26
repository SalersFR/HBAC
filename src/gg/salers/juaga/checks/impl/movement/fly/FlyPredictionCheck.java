package gg.salers.juaga.checks.impl.movement.fly;

import org.bukkit.Bukkit;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.PlayerUtils;

public class FlyPredictionCheck extends Check {

	private double lastDist, dist;
	private int preVL;
	private boolean wasWasCloseToGround, wasCloseToGround, isCloseToGround;

	public  FlyPredictionCheck() {
		super("Fly [Y Prediction]");
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if (packet.getType() == PacketType.POSITION) {
			Bukkit.broadcastMessage("packetuyareuyare");
			dist = jPlayer.getTo().getY() - jPlayer.getFrom().getY();
			double predictedDist = (lastDist - 0.08) * 0.9800000190734863D;
			this.lastDist = dist;
			isCloseToGround = PlayerUtils.isCloseToGround(jPlayer.getTo());
			this.wasCloseToGround = isCloseToGround;
			this.wasWasCloseToGround = wasCloseToGround;
			if (!jPlayer.isExemptedMove()) {
				if (!wasCloseToGround && !wasWasCloseToGround && !isCloseToGround) {
					if (Math.abs(dist - predictedDist) > 0.001) {
						if (++preVL > 20) {
							fail(jPlayer, "dist: " + dist + " predictedDist: " + predictedDist);
						}

					} else {
						preVL *= 0.75;
					}
				}

			}

		}
	}

}
