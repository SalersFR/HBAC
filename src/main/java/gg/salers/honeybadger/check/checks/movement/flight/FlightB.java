package gg.salers.honeybadger.check.checks.movement.flight;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

import java.io.IOException;

@CheckData(name = "Flight (B)", experimental = true)
public class FlightB extends Check {

    private double lastDeltaY;



    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {
            double lastDeltaY = this.lastDeltaY;
            this.lastDeltaY = playerData.getMovementProcessor().getDeltaY();
            double predictedDeltaY = (playerData.getMovementProcessor().getDeltaY() - 0.08) * 0.9800000190734863D;
            if(predictedDeltaY < 0.005)
                predictedDeltaY = 0;

            double result = Math.abs(playerData.getMovementProcessor().getDeltaY() - predictedDeltaY);
            if (playerData.getMovementProcessor().isInLiquid()
                    || playerData.getMovementProcessor().isNearBoat()
                    || playerData.getMovementProcessor().isInWeb()
                    || playerData.getMovementProcessor().isOnClimbable()) return;
            if(playerData.getMovementProcessor().getEdgeBlockTicks() > 5) return;


            if(playerData.getMovementProcessor().getAirTicks() > 15) {

                if(result > 0.01) {
                    if (++buffer > 2) {
                        setProbabilty((int) (result * 10));
                        flag(playerData, "r=" + result);
                    }
                }else buffer -= buffer > 0 ? 0.05 : 0;

            }


        }
    }
}
