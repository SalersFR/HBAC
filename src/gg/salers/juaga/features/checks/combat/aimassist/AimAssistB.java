package gg.salers.juaga.features.checks.combat.aimassist;

import java.util.ArrayList;
import java.util.List;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.MathUtils;

public class AimAssistB extends Check {

	private List<Float> pastsYawDiff = new ArrayList<>();

	public AimAssistB() {
		super("AimAssist", "B");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getTo() == null || jplayer.getFrom() == null)
			return;
		if (jPacket.getType() == PacketType.FLYING) {
			float yawDiff = jplayer.getTo().getYaw() - jplayer.getFrom().getYaw();

			if (yawDiff != 0) {
				pastsYawDiff.add(yawDiff);
				if (pastsYawDiff.size() >= 120) {
					double deviation = MathUtils.getDeviation(pastsYawDiff);
					// (deviation);
					/*
					 * TODO finish this check, it's a consistent rotations check
					 */
					pastsYawDiff.clear();
				}

			}

		}

	}

}
