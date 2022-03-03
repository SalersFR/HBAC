package gg.salers.honeybadger.check.checks.misc.protocol;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Protocol (B)", experimental = true)
public class ProtocolB extends Check {


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isUseEntity() && playerData.getCombatProcessor().getAttacked() == playerData.getPlayer()) {
            punish(playerData);
        }
    }
}
