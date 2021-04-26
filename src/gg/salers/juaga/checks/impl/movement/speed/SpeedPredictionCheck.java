package gg.salers.juaga.checks.impl.movement.speed;

import org.bukkit.Bukkit;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class SpeedPredictionCheck extends Check {
	
	private double lastDist, dist;
	private int preVL;
	private boolean wasOnGround, isOnGround;
	

	public SpeedPredictionCheck() {
		super("Speed [Prediction]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if(packet.getType() == PacketType.FLYING) {
			if(!jPlayer.isExemptedMove()) {
		
				double distX = jPlayer.getTo().getX() - jPlayer.getFrom().getX();
				double distZ = jPlayer.getTo().getZ() - jPlayer.getFrom().getZ();
				dist = Math.hypot(distX, distZ);
				this.dist = lastDist;
				isOnGround = jPlayer.getPlayer().isOnGround();
				this.wasOnGround = isOnGround;
				if(!isOnGround && !wasOnGround) {
					double predictedDist = lastDist * 0.91f;
			     
				}
				
				
				
			}
		}
		           
		
	}

}
