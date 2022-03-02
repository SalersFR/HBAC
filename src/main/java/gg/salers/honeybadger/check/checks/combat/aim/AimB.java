package gg.salers.honeybadger.check.checks.combat.aim;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.RotationProcessor;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Aim (B)", experimental = true)
public class AimB extends Check {

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isRot()) {

            final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

            final double deltaYaw = rotationProcessor.getDeltaYaw();
            final double pitch = rotationProcessor.getPitch();

            final double gcd = rotationProcessor.getGcdPitch();
            final boolean exempt = !(pitch < 82.5F && pitch > -82.5F) || deltaYaw < 5.0;


            if (gcd < 0.001D && !exempt) {
                if (++buffer > 7) {
                    flag(playerData, "gcd=" + gcd);
                }
            } else if (buffer > 0) buffer -= 0.05D;
        }
    }
}
