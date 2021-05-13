package gg.salers.juaga.features.checks.combat.aimassist;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.JPacketUseAction;
import gg.salers.juaga.packets.PacketType;

public class AimAssistA  extends Check{
	
	private float pastPastPitch,pastPitch,pitch;

	public AimAssistA() {
		super("AimAssist","A");
	}
	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if(jplayer.getTo() == null || jplayer.getFrom() == null) return;
		if(jPacket.getType() == PacketType.USE_ENTITY) {
			if(jplayer.getAction() == JPacketUseAction.ATTACK) {
				this.pitch = jplayer.getTo().getPitch();
				if(pitch == 0 && pastPitch < 0 && pastPastPitch == 0) { //a human can't do this
					fail(jplayer,"pitch=" + pitch + " pastPitch=" + pastPitch + " pastPastPitch=" + pastPastPitch);
				}
				this.pastPitch = pitch;
				this.pastPastPitch = pastPitch;
			}
		}
		
	}

}
