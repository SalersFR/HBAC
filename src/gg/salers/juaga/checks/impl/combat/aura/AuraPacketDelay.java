package gg.salers.juaga.checks.impl.combat.aura;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class AuraPacketDelay extends Check {

	private long lastFlying;
	private int preVL;

	protected AuraPacketDelay(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if (packet.getType() == PacketType.FLYING) {
			lastFlying = now();

		} else if (packet.getType() == PacketType.USE_ENTITY) {
			long packetDiff = now() - lastFlying;
			if (packetDiff < 25) {
				if (jPlayer.getPing() < 200) {
					if (++preVL > 20) {
						fail(jPlayer, "Packet delay: " + packetDiff);
					}
				}

			}else {
				preVL *= 0.75;
			}
		}

	}

}
