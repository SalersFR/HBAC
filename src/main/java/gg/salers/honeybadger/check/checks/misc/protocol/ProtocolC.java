package gg.salers.honeybadger.check.checks.misc.protocol;

import com.comphenix.protocol.PacketType;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Protocol (C)", experimental = true)
public class ProtocolC extends Check {

    /*
    Checks if a player sends invalid steer packets.
     */

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.getType() == PacketType.Play.Client.STEER_VEHICLE) {
            float sideways = packet.getContainer().getFloat().readSafely(0), forwards = packet.getContainer().getFloat().readSafely(1);

            if(Math.abs(sideways) > 0.98F || Math.abs(forwards) > 0.98F) {
                flag(playerData, "f=" + String.format("%.2f", forwards) + " s=" + sideways);
            }

        }
    }
}
