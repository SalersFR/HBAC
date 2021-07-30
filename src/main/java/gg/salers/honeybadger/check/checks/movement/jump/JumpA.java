package gg.salers.honeybadger.check.checks.movement.jump;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.PlayerUtils;
import org.bukkit.potion.PotionEffectType;

@CheckData(name = "Jump (A)", experimental = true)
public class JumpA extends Check {


    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.POSITION
                || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            double limit = 0.42F; //maximum height of jumping
            limit += PlayerUtils.getPotionLevel(playerData.getBukkitPlayerFromUUID(), PotionEffectType.JUMP) * 0.1F;

            if (playerData.getMovementProcessor().getAirTicks() > 1 && playerData.getMovementProcessor().getAirTicks() < 5) {
                if (playerData.getBukkitPlayerFromUUID().getLocation().add(0, 0.999, 0).getBlock().isEmpty()) {
                    if (playerData.getMovementProcessor().getDeltaY() > limit) {
                        flag(playerData, "dY=" + playerData.getMovementProcessor().getDeltaY());
                    }
                }
            }
        }
    }
}
