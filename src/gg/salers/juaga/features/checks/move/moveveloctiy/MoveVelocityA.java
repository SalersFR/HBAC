package gg.salers.juaga.features.checks.move.moveveloctiy;

import org.bukkit.Material;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class MoveVelocityA extends Check {
	
	private double deltaXZ, motionXZ;

	public MoveVelocityA() {
		super("MoveVelocity","A");
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
			motionXZ = Math.hypot(jplayer.getPlayer().getVelocity().getX(), jplayer.getPlayer().getVelocity().getZ());
			double result = deltaXZ - motionXZ; //a kind of preditction
			
			if(result > 0.68) {
				lagBack(jplayer);
				fail(jplayer ," motionXZ=" + motionXZ + " deltaXZ=" + deltaXZ + " result=" + result);
				
			}

		

		}
		
	}

}
