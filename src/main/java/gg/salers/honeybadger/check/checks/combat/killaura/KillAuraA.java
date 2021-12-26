package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "KillAura (A)", experimental = false)
public class KillAuraA extends Check {



    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isAttack()) {
            long deltaFlying = Math.abs(System.currentTimeMillis() - playerData.getNetworkProcessor().getLastFlying());
            if (deltaFlying < 25L) {
                if (playerData.getNetworkProcessor().getKpPing() < 175) {
                    if(++buffer > 5) {
                        setProbabilty((int) deltaFlying);
                        flag(playerData, "dF=" + deltaFlying);
                    }
                }
            }else buffer = 0;
        }
    }
}
