package gg.salers.honeybadger.check.checks.misc.protocol;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Protocol (A)", experimental = true)
public class ProtocolA extends Check {

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isMove() && Math.abs(playerData.getRotationProcessor().getPitch()) > 90f) {
            punish(playerData);
        }
    }
}
