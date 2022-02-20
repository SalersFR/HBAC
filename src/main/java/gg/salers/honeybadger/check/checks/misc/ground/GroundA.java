package gg.salers.honeybadger.check.checks.misc.ground;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.CollisionProcessor;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Ground (A)", experimental = true)
public class GroundA extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {
            final CollisionProcessor collisionProcessor = playerData.getCollisionProcessor();

            if (collisionProcessor.getMathAirTicks() > 9 && collisionProcessor.getClientGroundTicks() > 0 && !collisionProcessor.isOnSlime()) {
                if (++buffer > 3)
                    flag(playerData, "spoofed client ground.");
            } else if (buffer > 0) buffer -= 0.125D;

        }

    }
}

