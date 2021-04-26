package gg.salers.juaga.checks.impl.combat.autoclicker;

import java.util.ArrayList;
import java.util.List;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.MathUtils;

public class AutoClickerConsistentDelayCheck extends Check {

	private boolean wasNotDigging, isNotDigging;
	private long clickDelay, lastClickTime, clickTime;
	private List<Long> pastDelays = new ArrayList<>();

	public  AutoClickerConsistentDelayCheck() {
		super("AutoClicker [Consistent Delay between clicks]") ;
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if (packet.getType() == PacketType.ARM_ANIMATION) {
			if (wasNotDigging && isNotDigging) {
				clickTime = now();
				clickDelay = clickTime - lastClickTime;
				pastDelays.add(clickDelay);
				double deviation = MathUtils.getStandardDeviation(pastDelays);
				if (pastDelays.size() > 120) {
					if (deviation < 0.01) {
						fail(jPlayer);
					}
					pastDelays.clear();
				}
			}
			this.lastClickTime = clickTime;

		} else if (packet.getType() == PacketType.DIGGING) {
			isNotDigging = true;
			this.wasNotDigging = isNotDigging;
		} else if (packet.getType() == PacketType.FLYING) {
			isNotDigging = false;
			this.wasNotDigging = isNotDigging;
		}

	}

}
