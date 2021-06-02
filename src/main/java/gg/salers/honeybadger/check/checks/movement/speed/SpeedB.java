package gg.salers.honeybadger.check.checks.movement.speed;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.LocationUtils;
import org.bukkit.Material;

import java.time.LocalDate;

@CheckData(name = "Speed (B)", experimental = true)
public class SpeedB extends Check {

    private boolean wasOnGround;


    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.POSITION || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            boolean isOnGround = playerData.getBukkitPlayerFromUUID().isOnGround();
            boolean wasOnGround = this.wasOnGround;
            this.wasOnGround = isOnGround;
            double limit = 0.905;
            limit += playerData.getBukkitPlayerFromUUID().getWalkSpeed() > 0.2 ? 0 :
                    playerData.getBukkitPlayerFromUUID().getWalkSpeed();
            if (playerData.getBukkitPlayerFromUUID().getLocation().add(0, -1.25, 0).getBlock().getType()
                    == Material.ICE || playerData.getBukkitPlayerFromUUID().getLocation().add(0,-1.25,0).
                    getBlock().getType() == Material.PACKED_ICE) {
                limit += 0.6;

            }
            if(isOnGround && wasOnGround) {
                limit = 0.4401;
            }
            if(!playerData.getBukkitPlayerFromUUID().getLocation().add(0,0.899,0).getBlock().isEmpty()) {
                limit = 1.31;
            }

            if(playerData.getMovementProcessor().getDeltaXZ() > limit) {
                flag(playerData,"l=" + limit + " d=" + playerData.getMovementProcessor().getDeltaXZ());
            }
        }


    }
}
