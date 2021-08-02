package gg.salers.honeybadger.check.checks.misc.ground;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Ground (A)", experimental = true)
public class GroundA extends Check {

    @Override
    public void onPacket(Packet packet, PlayerData playerData) {
        if (packet.isFlying()) {
            if (playerData.getMovementProcessor().getAirTicks() > 15
                    && !playerData.getMovementProcessor().isAtTheEdgeOfABlock()
                    && !playerData.getMovementProcessor().isOnClimbable()
                    && !playerData.getMovementProcessor().isNearBoat()
                    && playerData.getBukkitPlayerFromUUID().isOnGround()) {
                flag(playerData, "aT=" + playerData.getMovementProcessor().getAirTicks());
            }
        }
    }
}
