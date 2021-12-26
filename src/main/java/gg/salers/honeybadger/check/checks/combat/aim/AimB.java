package gg.salers.honeybadger.check.checks.combat.aim;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.RotationProcessor;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.MathUtils;

public class AimB extends Check {

    private double lastDeltaPitch;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isRot()) {

            final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

            final double deltaYaw = rotationProcessor.getDeltaYaw();
            final double deltaPitch = rotationProcessor.getDeltaPitch();

            final double pitch = rotationProcessor.getPitch();

            final double lastDeltaPitch = this.lastDeltaPitch;
            this.lastDeltaPitch = deltaPitch;

            final double gcd = MathUtils.getGcd(deltaPitch,lastDeltaPitch);

            final boolean exempt = !(pitch < 82.5F && pitch > -82.5F) || deltaYaw < 5.0 || !playerData.getCombatProcessor().isAttacking();

            if(gcd <= 0.0D && !exempt) {
                if(++buffer > 7) {
                    flag(playerData,"gcd=" + gcd) ;
                    buffer -= 1.25;
                }
            } else buffer -= 0.25D;
        }
    }
}
