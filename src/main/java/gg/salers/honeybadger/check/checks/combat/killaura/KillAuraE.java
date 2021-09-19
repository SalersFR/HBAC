package gg.salers.honeybadger.check.checks.combat.killaura;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.MovementProcessor;
import gg.salers.honeybadger.utils.HPacket;
import org.bukkit.entity.EntityType;

@CheckData(name = "KillAura (E)",experimental = true)
public class KillAuraE extends Check {

    private int ticks;
    private double lastDeltaXZ;
    private double buffer;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isAttack()) {
            this.ticks = 0;
        }else if(packet.isFlying()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final double deltaXZ = movementProcessor.getDeltaXZ();
            final double lastDeltaXZ = this.lastDeltaXZ;

            this.lastDeltaXZ = deltaXZ;

            final double accelerationXZ = Math.abs(deltaXZ - lastDeltaXZ);

            if(deltaXZ > 0.19D
                    && playerData.getBukkitPlayerFromUUID().isSprinting()
                    && accelerationXZ < 0.00001D
                    && playerData.getCombatProcessor().getAttacked().getType() == EntityType.PLAYER && ticks <= 2) {

                if(++buffer > 6) {
                    buffer /= 2;
                    flag(playerData,"accel=" + accelerationXZ);
                }



            } else if(buffer > 0) buffer -= 2.5D;


            this.ticks++;
        }
    }
}
