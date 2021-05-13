package gg.salers.juaga.features.checks.move.fly;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.PacketType;
import gg.salers.juaga.utils.LocationUtils;

public class FlyA extends Check {

	private float threshold;
	private double lastResult,lastDeltaY;
	private boolean wasWasCloseToGround, wasCloseToGround, isCloseToGround;
	

	public FlyA() {
		super("Fly", " A");
	}

	@Override
	public void handle(JPacket jPacket, JPlayer jplayer) {
		if (jplayer.getFrom() == null || jplayer.getFrom() == null)
			return;
		if(jPacket.getType() == PacketType.POSITION) {
			isCloseToGround = LocationUtils.isCloseToGround(jplayer.getTo());
			boolean wasCloseToGround = this.wasCloseToGround;
			wasCloseToGround = isCloseToGround;
			boolean wasWasCloseToGround = this.wasWasCloseToGround;
			wasWasCloseToGround = wasCloseToGround;
			if(!wasWasCloseToGround && !wasCloseToGround && !isCloseToGround) {
				double deltaY = (jplayer.getTo().getY() - jplayer.getFrom().getY());
				double lastDeltaY = this.lastDeltaY;
				this.lastDeltaY = deltaY;
				double predictedDeltaY = (lastDeltaY - 0.08) * 0.9800000190734863D;
				double result = jplayer.getDeltaY() - predictedDeltaY;
				
				
				double lastResult = this.lastResult;
				this.lastResult = result;
				
				if(result > 0.1 || (lastResult == 0.0784000015258789 && result == 0.0784000015258789)) {
					if(++threshold > 2) {
						lagBack(jplayer);
						fail(jplayer, "lastResult=" + lastResult + " result=" + result + " predictedDeltaY=" + predictedDeltaY + " deltaY=" + jplayer.getDeltaY());
					}
				}else threshold -= threshold > 0 ? 1 : 0;

 
			}  
		}
	
	}

}
