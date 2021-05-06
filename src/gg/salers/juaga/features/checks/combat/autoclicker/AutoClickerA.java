package gg.salers.juaga.features.checks.combat.autoclicker;

import java.util.ArrayList;
import java.util.List;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.MathUtils;

public class AutoClickerA extends Check {
	

	
	//idea from joshi, improved a lot by me

	private int ticks;
	private List<Double> pastClicks = new ArrayList<>();
	private float threshold;
	
	public AutoClickerA() {
		super("AutoClicker ","A");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		
		if (jPacket.getType() == PacketType.ARM_ANIMATION) {
			
			pastClicks.add((double)ticks);
			double deviation = MathUtils.getDeviation(pastClicks);
			
			if(pastClicks.size() > 100) {
				
				if(deviation < 4.75) {
				if(++threshold > 5) {
					fail(jplayer);
				}
					
				}
				pastClicks.clear();
			}else threshold *= 0.95;

			ticks = 0;
		} else if (jPacket.getType() == PacketType.FLYING) {
			ticks++;
		}

	}

}
