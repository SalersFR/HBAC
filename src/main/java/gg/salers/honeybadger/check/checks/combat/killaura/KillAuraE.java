package gg.salers.honeybadger.check.checks.combat.killaura;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;
import org.bukkit.entity.EntityType;

@CheckData(name = "KillAura (E)", experimental = true)
public class KillAuraE extends Check {

    private int ticks, buffer;
    private double lastDeltaXZ;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isAttack()) {
            ticks = 0;
        } else if (packet.isFlying()) {

            if (playerData.getCombatProcessor().getLastAttacked() == null) return;

            final double deltaXZ = playerData.getMovementProcessor().getDeltaXZ();
            final double lastDeltaXZ = playerData.getMovementProcessor().getLastDeltaXZ();

            final double accelerationXZ = Math.abs(deltaXZ - lastDeltaXZ);

            if (deltaXZ > 0.16 && playerData.getBukkitPlayerFromUUID().isSprinting()
                    && accelerationXZ < 0.005D && ticks <= 2 && playerData.getCombatProcessor().
                    getLastAttacked().getType() == EntityType.PLAYER) {
                if (++buffer > 7) {
                    buffer /= 2;
                    flag(playerData, "a=" + accelerationXZ);
                }
            } else if (buffer > 0) buffer -= 0.125D;


            this.ticks++;

        }

    }
}
