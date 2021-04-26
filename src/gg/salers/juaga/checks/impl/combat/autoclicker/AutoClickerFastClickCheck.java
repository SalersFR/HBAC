package gg.salers.juaga.checks.impl.combat.autoclicker;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;

public class AutoClickerFastClickCheck extends Check {

	private boolean wasDigging, isDigging;
	private int preVL, ticks, clicks;

	public  AutoClickerFastClickCheck() {
		super("AutoClicker [FastClick]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if (packet.getType() == PacketType.ARM_ANIMATION) {
			if (!wasDigging && !isDigging) {
				clicks++;
			}

		} else if (packet.getType() == PacketType.FLYING) {
			isDigging = false;
			this.wasDigging = isDigging;
			if (++ticks >= 20) {
				if(clicks > 19) {
					if(++preVL > 10) {
						fail(jPlayer);
					}
				}else {
					preVL *= 0.825;
				}
				clicks = 0;
				ticks = 0;

			}
		} else if (packet.getType() == PacketType.DIGGING) {
			isDigging = true;
			this.wasDigging = isDigging;
		}

	}

}
