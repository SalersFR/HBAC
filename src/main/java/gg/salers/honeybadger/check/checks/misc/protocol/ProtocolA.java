package gg.salers.honeybadger.check.checks.misc.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Protocol (A)", experimental = true)
public class ProtocolA extends Check {

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if(event.getPacketType() == PacketType.Play.Client.POSITION_LOOK
                || event.getPacketType() == PacketType.Play.Client.LOOK) {
            if(Math.abs(event.getPlayer().getLocation().getPitch()) > 90f) {
                punish(playerData);
            }

        }

    }
}
