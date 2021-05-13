package gg.salers.juaga.features.checks.combat.reach;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.util.Vector;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.JPacketUseAction;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.JLocation;
import gg.salers.juaga.utils.LocationUtils;
import gg.salers.juaga.utils.MathUtils;

public class ReachA extends Check {

	private float threshold;
	List<JLocation> pastVictimLocations = new LinkedList<>();
	private int ticks;

	public ReachA() {
		super("Reach ", "A");
	}

	/*
	 * Here is a 3.3 Reach Detection, should not false too much
	 */
	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getAttacked() == null)
			return;

		if (jPacket.getType() == PacketType.USE_ENTITY) {
			if (jplayer.getAction() == JPacketUseAction.ATTACK) {
				try {
					List<Vector> pastLocations = LocationUtils
							.getEstimatedLocation(pastVictimLocations, MathUtils.msToTicks(jplayer.getPing())).stream()
							.map(vec -> vec.toVector(jplayer.getPlayer().getWorld())).collect(Collectors.toList()); // getting mutliples locations(idea from what funkee said)
																												
					if (pastVictimLocations.size() < 1) {
						pastLocations.clear();
						return;
					}
					float distance = (float) pastLocations.stream().mapToDouble(vec -> MathUtils
							.getHorizontalDistanceToHitBox(vec, jplayer.getPlayer().getLocation().toVector())).min()
							.orElse(1);
					if (distance > 10f)
						return;

					if (distance > 3.05f) {
						if (++threshold > 7.5) {
							fail(jplayer , " distance=" + distance + " threshold=" + threshold);

						}
					} else
						threshold *= 0.95;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} else if (jPacket.getType() == PacketType.REL_MOVE) {
			pastVictimLocations.add(new JLocation(jplayer.getAttacked().getLocation().getX(),
					jplayer.getAttacked().getLocation().getY(), jplayer.getAttacked().getLocation().getZ(),
					System.currentTimeMillis()));

		} else if (jPacket.getType() == PacketType.FLYING || jPacket.getType() == PacketType.LOOK
				|| jPacket.getType() == PacketType.POSITION || jPacket.getType() == PacketType.POSITION_LOOK) {

			ticks++;
			if (ticks >= 20) {
				ticks = 0;
				pastVictimLocations.clear();
			}

		}

	}

}
