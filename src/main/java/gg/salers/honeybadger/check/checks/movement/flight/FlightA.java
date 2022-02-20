package gg.salers.honeybadger.check.checks.movement.flight;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.CollisionProcessor;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Flight (A)", experimental = true)
public class FlightA extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {

            final double accelY = Math.abs(playerData.getMovementProcessor().getDeltaY() - playerData.getMovementProcessor().getLastDeltaY());
            final CollisionProcessor collisionProcessor = playerData.getCollisionProcessor();

            final boolean exempt = collisionProcessor.isInLiquid() || collisionProcessor.isNearBoat() ||
                    collisionProcessor.isInWeb() || collisionProcessor.isOnClimbable();


            if (collisionProcessor.getClientAirTicks() > 9 && !exempt) {
                if (accelY < 0.0001) {
                    if (++buffer > 5)
                        setProbabilty((int) (accelY + 1));
                    flag(playerData, "aY=" + accelY);
                } else buffer -= buffer > 0 ? 0.2 : 0;

            }


        }
    }
}
