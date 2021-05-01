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

	/*
	 * Here is a 3.3 Reach Detection, should not false too much
	 */
	public ReachA() {
		super("Reach (A)");
	}
	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getAttacked() == null)
			return;
		if (jPacket.getType() == PacketType.USE_ENTITY) {
			if (jplayer.getAction() == JPacketUseAction.ATTACK) {

				if (pastVictimLocations.size() < 1)
					return;

				Location attackerLocation = jplayer.getPlayer().getLocation(); // getting the basic location of the attacker
																			
				double victimPing = jplayer.getPing(); // getting the ping of the victim with packets
				if (victimPing == 0)
					victimPing = 51; // for bots or no-ping cheats

				try {
					Location victimLocation = pastVictimLocations
							.get(pastVictimLocations.size() - MathUtils.msToTicks(victimPing)); // try to get the perfect location with ping in tick (raytrace)
																								
					Vector victim = victimLocation.toVector().setY(0); // so with that we don't care about y
					Vector attacker = attackerLocation.toVector().setY(0);
					float distance = (float) (MathUtils.getHorizontalDistanceToHitBox(victim, attacker)); // distance calcul with taking care of 1.8 hitbox
																											
					if (distance > 15f)
						return; // for patching a bug
					float distanceBukkit = (float) jplayer.getAttacked().getLocation()
							.distance(jplayer.getPlayer().getLocation()); // for being sure if the player is cheating

					if (distance > 3.1f && distanceBukkit > 3.1f) {
						if (++threshold > 7.5f) {
							fail(jplayer);
						}
					} else
						threshold *= 0.775;// for prevent falses
					
			

				} catch (IndexOutOfBoundsException e) {
					return;

				}
			}

		} else if (jPacket.getType() == PacketType.FLYING) {
			pastVictimLocations.add(jplayer.getAttacked().getLocation());

		}
	}

}
