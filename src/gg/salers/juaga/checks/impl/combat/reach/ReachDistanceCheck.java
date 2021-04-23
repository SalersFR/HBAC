package gg.salers.juaga.checks.impl.combat.reach;

import org.bukkit.Material;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.JPlayerUseAction;

public class ReachDistanceCheck extends Check {
	
	public ReachDistanceCheck() {
		super("Reach [Distance]");
		// TODO Auto-generated constructor stub
	}



	private double lastDistXZMove, distXZMove;
	private int preVL;
	
	

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if(packet.getType() == PacketType.USE_ENTITY) {
			if(jPlayer.getAction() == JPlayerUseAction.ATTACK) {
				double distanceX = jPlayer.getLastTarget().getLocation().getX() - jPlayer.getPlayer().getLocation().getX();
				double distanceZ = jPlayer.getLastTarget().getLocation().getZ() - jPlayer.getPlayer().getLocation().getZ();
				double distance = Math.hypot(distanceX, distanceZ);
				double substracter = (distXZMove - lastDistXZMove) + 
						(jPlayer.getLastTarget().getVelocity().length() * 2.5) + 
						(jPlayer.getLastTarget().getVelocity().getX() * 2.5) +
						 (jPlayer.getLastTarget().getVelocity().getY() * 2.5) + 
						(jPlayer.getLastTarget().getVelocity().getZ() * 2.5);
				distance -= substracter;
				double maxReach = 3.4;
				if(jPlayer.getLastTarget().getLocation().add(0,-0.4001, 0).getBlock().getType() != Material.AIR) {
					maxReach = 3.2;
				}
				if(distance > maxReach ) {
					if(++preVL > 10) {
						fail(jPlayer);
					}
					
				}
			}
		}else if(packet.getType() == PacketType.POSITION) {
			distXZMove = Math.hypot((jPlayer.getTo().getX() +  jPlayer.getTo().getZ()),  (jPlayer.getFrom().getX() +  jPlayer.getFrom().getZ()));
			this.lastDistXZMove = distXZMove;
			 
		}
		
	}

}
