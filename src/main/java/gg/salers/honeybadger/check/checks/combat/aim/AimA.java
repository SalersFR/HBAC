package gg.salers.honeybadger.check.checks.combat.aim;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Aim (A)", experimental = true)
public class AimA extends Check {

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isRot()) {
            final double yawAccel = playerData.getRotationProcessor().getYawAccel();

            if (playerData.getRotationProcessor().getDeltaPitch() == 0.00D && yawAccel > 29.5) {
                if (++buffer > 3) {
                    flag(playerData, "yA=" + (float) yawAccel + " pA=" + (float) playerData.getRotationProcessor().getDeltaPitch());
                }
            } else buffer -= buffer > 0 ? 0.25 : 0;





        }
    }
}
