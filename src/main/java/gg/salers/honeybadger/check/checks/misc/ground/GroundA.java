package gg.salers.honeybadger.check.checks.misc.ground;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Ground (A)", experimental = true)
public class GroundA extends Check {



    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isMove()) {


            if(playerData.getMovementProcessor().getAirTicks() > 15 && !playerData.getMovementProcessor().
                    isAtTheEdgeOfABlock() && !playerData.getMovementProcessor().isOnClimbable() && !playerData.
                    getMovementProcessor().isNearBoat() && playerData.getBukkitPlayerFromUUID().isOnGround() &&
                    !playerData.getMovementProcessor().isMathGround())  {
                flag(playerData,"aT=" + playerData.getMovementProcessor().getAirTicks());
            }

        }
    }
}
