package gg.salers.honeybadger.check.checks.misc.protocol.ground;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Ground (A)", experimental = true)
public class GroundA extends Check {

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.POSITION ||
                event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            if(playerData.getMovementProcessor().getAirTicks() > 15 && !playerData.getMovementProcessor().
                    isAtTheEdgeOfABlock() && !playerData.getMovementProcessor().isOnClimbable() && !playerData.
                    getMovementProcessor().isNearBoat() && playerData.getBukkitPlayerFromUUID().isOnGround())  {
                flag(playerData,"aT=" + playerData.getMovementProcessor().getAirTicks());
            }

        }

    }
}
