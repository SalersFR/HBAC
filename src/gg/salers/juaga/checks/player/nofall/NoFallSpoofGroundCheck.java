package gg.salers.juaga.checks.player.nofall;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.PlayerUtils;

public class NoFallSpoofGroundCheck extends Check{
	
	private boolean wasWasCloseToGround,wasCloseToGround,isCloseToGround;
	private boolean wasClientOnGround, isClientOnGround;

	public  NoFallSpoofGroundCheck() {
		super("NoFall [Spoofing Ground]");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(JPacket packet, JPlayer jPlayer) {
		if(packet.getType() == PacketType.FLYING) {
			isCloseToGround = PlayerUtils.isCloseToGround(jPlayer.getTo());
			isClientOnGround = jPlayer.getPlayer().isOnGround();
			if(!wasWasCloseToGround && !wasCloseToGround && !isCloseToGround) {
				if(wasClientOnGround && isClientOnGround) {
					fail(jPlayer);
				}
			}
			this.wasCloseToGround = isCloseToGround;
			this.wasWasCloseToGround = wasCloseToGround;
			this.wasClientOnGround = isClientOnGround;
		}
		
	}

}
