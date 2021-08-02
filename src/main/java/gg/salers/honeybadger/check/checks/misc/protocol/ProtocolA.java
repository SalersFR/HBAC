package gg.salers.honeybadger.check.checks.misc.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Protocol (A)", experimental = true)
public class ProtocolA extends Check {

    @Override
    public void onPacket(Packet packet, PlayerData playerData) {
        if (packet.isFlying()) {
            final float pitch = Math.abs(playerData.getBukkitPlayerFromUUID().getLocation().getPitch());

            if (pitch > 90f) {
                punish(playerData);
            }
        }
    }
}
