package gg.salers.honeybadger.check.checks.movement.jump;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.CollisionProcessor;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.PlayerUtils;
import org.bukkit.potion.PotionEffectType;

@CheckData(name = "Jump (A)", experimental = true)
public class JumpA extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {
            final double jumpMotion = 0.42F + (playerData.getPlayer().hasPotionEffect(PotionEffectType.JUMP)
                    ? PlayerUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.JUMP) * 0.1F : 0); //maximum height of jumping

            final CollisionProcessor collisionProcessor = playerData.getCollisionProcessor();

            final boolean exempt = collisionProcessor.isOnSlime() ||
                    collisionProcessor.isLastGroundSlime() || collisionProcessor.isInLiquid() || collisionProcessor.isBlockNearHead();
            final boolean jumped = collisionProcessor.getClientAirTicks() == 1 && playerData.getMovementProcessor().getDeltaY() > 0;

            if (jumped && !exempt && playerData.getVelocityProcessor().getVelTicks() > 10) {
                if (playerData.getMovementProcessor().getDeltaY() != jumpMotion) {
                    if (++buffer > 5)
                        flag(playerData, "dY=" + playerData.getMovementProcessor().getDeltaY());
                } else if (buffer > 0) buffer -= 0.2;
            }


        }


    }
}

