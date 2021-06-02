package gg.salers.honeybadger.check.checks.movement.flight;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Flight (A)", experimental = true)
public class FlightA extends Check {

    private double lastDeltaY;
    private int threshold;

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.POSITION || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {

            double lastDeltaY = this.lastDeltaY;
            this.lastDeltaY = playerData.getMovementProcessor().getDeltaY();
            double accelY = Math.abs(playerData.getMovementProcessor().getDeltaY() - lastDeltaY);
            if (playerData.getMovementProcessor().isInLiquid()
                    || playerData.getMovementProcessor().isNearBoat()
                    || playerData.getMovementProcessor().isInWeb()
                    || playerData.getMovementProcessor().isOnClimbable()) return;
            if(playerData.getMovementProcessor().getEdgeBlockTicks() > 5) return;

            if (playerData.getMovementProcessor().getAirTicks() > 15 && !playerData.getMovementProcessor().isAtTheEdgeOfABlock()) {
                if (accelY < 0.0001) {
                    if (++threshold > 15)
                        setProbabilty((int) (accelY + 1));
                        flag(playerData, "aY=" + accelY);
                } else threshold -= threshold > 0 ? 1 : 0;

            }


        }

    }


}
