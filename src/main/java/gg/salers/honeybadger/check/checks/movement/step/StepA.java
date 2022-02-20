package gg.salers.honeybadger.check.checks.movement.step;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Step (A)", experimental = true)
public class StepA extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {

            if (playerData.getCollisionProcessor().getClientGroundTicks() > 2 && playerData.getMovementProcessor().
                    getDeltaY() > 0.6 && playerData.getMovementProcessor().getDeltaXZ() > 0.1D) {
                flag(playerData, "dY=" + playerData.getMovementProcessor().getDeltaY());
            }
        }
    }
}
