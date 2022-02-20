package gg.salers.honeybadger.check.checks.movement.flight;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.CollisionProcessor;
import gg.salers.honeybadger.processor.impl.MovementProcessor;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Flight (C)", experimental = false)
public class FlightC extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {

            final CollisionProcessor collisionProcessor = playerData.getCollisionProcessor();
            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final boolean exempt = collisionProcessor.isInLiquid() || collisionProcessor.isNearBoat() ||
                    collisionProcessor.isInWeb() || collisionProcessor.isOnClimbable();

            final double deltaY = movementProcessor.getDeltaY();
            final double lastDeltaY = movementProcessor.getLastDeltaY();

            final boolean inAir = collisionProcessor.getClientAirTicks() > 9;

            if (inAir && !exempt) {
                if (deltaY > lastDeltaY) {
                    if (++buffer > 2) {
                        setProbabilty(1);
                        flag(playerData, "dY=" + playerData.getMovementProcessor().getDeltaY() + " lDY=" + lastDeltaY);
                    }
                } else buffer -= buffer > 0 ? 0.05 : 0;

            }
        }
    }
}


