package gg.salers.honeybadger.check.checks.movement.flight;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Flight (C)", experimental = false)
public class FlightC extends Check {

    private double lastDeltaY;



    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {
            double lastDeltaY = this.lastDeltaY;
            this.lastDeltaY = playerData.getMovementProcessor().getDeltaY();
            if (playerData.getMovementProcessor().isInLiquid()
                    || playerData.getMovementProcessor().isNearBoat()
                    || playerData.getMovementProcessor().isInWeb()
                    || playerData.getMovementProcessor().isOnClimbable()) return;
            if (playerData.getMovementProcessor().getEdgeBlockTicks() > 5) return;

            if (playerData.getMovementProcessor().getAirTicks() > 15) {
                if (playerData.getMovementProcessor().getDeltaY() > lastDeltaY) {
                    if (++buffer > 2) {
                        setProbabilty(1);
                        flag(playerData, "dY=" + playerData.getMovementProcessor().getDeltaY() + " lDY=" + lastDeltaY);
                    }
                } else buffer -= buffer > 0 ? 0.05 : 0;

            }
        }
    }
}


