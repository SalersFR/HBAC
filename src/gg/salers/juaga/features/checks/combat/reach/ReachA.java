package gg.salers.juaga.features.checks.combat.reach;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.util.Vector;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.JPacketUseAction;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.MathUtils;

public class ReachA extends Check {

	private float threshold;
	List<Location> pastVictimLocations = new ArrayList<>();

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jplayer.getAttacked() == null ) return;
		if(jPacket.getType() == PacketType.USE_ENTITY) {
			if(jplayer.getAction() == JPacketUseAction.ATTACK) {
			
			pastVictimLocations.add(jplayer.getAttacked().getLocation());
			
			if(pastVictimLocations.size() < 1) return;
		
			Location attackerLocation = jplayer.getPlayer().getLocation(); //getting the basic location of the attacker
			int victimPing = ((CraftPlayer)jplayer.getAttacked()).getHandle().ping; //getting the ping of the victim
			if( victimPing == 0) victimPing = 49; //for bots or no-ping cheats
			Location victimLocation = pastVictimLocations.get(pastVictimLocations.size() - MathUtils.msToTicks((double)victimPing)); //try to get the location with victim's ping in ticks
			Vector victim = victimLocation.toVector().setY(0); //so with that we don't care about y
			Vector attacker = attackerLocation.toVector().setY(0);
			float distance = (float) (MathUtils.getHorizontalDistanceToHitBox(victim, attacker)); // 0.56569 (credits to minecraftanticheatcommunity for this number) due to hitbox and some stuff
			if(distance > 15f) return; //for patching a bug 
			
			if(distance > 3.1f) {
				if(++threshold > 7.5f) {
					fail(jplayer, "Reach (A)");
				}
			}else threshold *= 0.75;
			
		}else if(jPacket.getType() == PacketType.FLYING) {
			pastVictimLocations.add(jplayer.getAttacked().getLocation());//every tick, adding the victim in location in a tick.
		}
		
	}
}

}
