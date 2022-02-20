package gg.salers.honeybadger.check.checks.movement.flight;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.CollisionProcessor;
import gg.salers.honeybadger.processor.impl.MovementProcessor;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Flight (B)", experimental = true)
public class FlightB extends Check {


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

            final double expected = (lastDeltaY - 0.08) * 0.98F;
            final double threshold = collisionProcessor.getCollisionAirTicks() > 7 ? 0.001 : 0.01;

            final double accuracy = Math.abs(expected - deltaY);


            if (inAir && !exempt) {
                if (accuracy > threshold) {
                    if (++buffer > 5) {
                        setProbabilty((int) (accuracy * 10));
                        flag(playerData, "acc=" + accuracy);
                    }
                } else buffer -= buffer > 0 ? 0.125 : 0;

            }


        }
    }
}
