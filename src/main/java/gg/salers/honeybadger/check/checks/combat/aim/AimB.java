package gg.salers.honeybadger.check.checks.combat.aim;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.RotationProcessor;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.MathUtils;
import org.bukkit.Bukkit;

@CheckData(name = "Aim (B)", experimental = true)
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

            final double gcd = MathUtils.getGcd1((long)deltaPitch, (long)lastDeltaPitch);

            final boolean exempt = !(pitch < 82.5F && pitch > -82.5F) || deltaYaw < 5.0 || !playerData.getCombatProcessor().isAttacking();
            if(gcd <= 0.000001D) {
                Bukkit.broadcastMessage("buffer=" + buffer);
            }

            if(gcd <= 0.000001D && !exempt) {
                if(++buffer > 7) {
                    flag(playerData,"gcd=" + gcd) ;
                }
            } else if(buffer > 0) buffer -= 0.05D;
        }
    }
}
