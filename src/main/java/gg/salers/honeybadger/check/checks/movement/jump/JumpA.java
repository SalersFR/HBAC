package gg.salers.honeybadger.check.checks.movement.jump;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.PlayerUtils;
import org.bukkit.potion.PotionEffectType;

@CheckData(name = "Jump (A)", experimental = true)
public class JumpA extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove()) {
            double limit = 0.42F; //maximum height of jumping
            limit += PlayerUtils.getPotionLevel(playerData.getBukkitPlayerFromUUID(), PotionEffectType.JUMP) * 0.1;
            if (playerData.getMovementProcessor().getAirTicks() > 1 && !playerData.getBukkitPlayerFromUUID().isOnGround()) {
                if (playerData.getBukkitPlayerFromUUID().getLocation().add(0, 0.4201D, 0).getBlock().isEmpty()) {
                    if (playerData.getMovementProcessor().getDeltaY() > limit) {
                        flag(playerData, "dY=" + playerData.getMovementProcessor().getDeltaY());
                    }
                }


            }


        }
    }
}
