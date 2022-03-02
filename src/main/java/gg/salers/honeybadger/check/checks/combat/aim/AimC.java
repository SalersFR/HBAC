package gg.salers.honeybadger.check.checks.combat.aim;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.RotationProcessor;
import gg.salers.honeybadger.utils.HPacket;

/**
 * @author Salers
 * Made the 20/02/2022
 */

@CheckData(name = "Aim (C)", experimental = true)
public class AimC extends Check {

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isRot()) {
            final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

            final double deltaYaw = rotationProcessor.getDeltaYaw();
            final double pitch = rotationProcessor.getPitch();

            final double gcd = rotationProcessor.getExpandedGcdPitch();
            final boolean exempt = !(pitch < 82.5F && pitch > -82.5F) || deltaYaw < 5.0 || Math.abs(deltaYaw - rotationProcessor.getLastDeltaYaw()) < 0.1;



            if (gcd < 131072L && !exempt) {
                if (buffer < 15) buffer++;

                if (buffer > 8)
                    flag(playerData, "gcd=" + gcd);

            } else if (buffer >= 1.5D) buffer -= 0.4D;
        }

    }
}
