package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "KillAura (A)", experimental = false)
public class KillAuraA extends Check {


    private int vl;

    @Override
    public void onPacket(Packet packet, PlayerData playerData) {
        if (packet.isAttack()) {
            if (playerData.getCombatProcessor().getAction() == EnumWrappers.EntityUseAction.ATTACK) {
                long deltaFlying = Math.abs(System.currentTimeMillis() - playerData.getNetworkProcessor().getLastFlying());

                if (deltaFlying < 25L) {
                    if(++vl > 4) {
                        if (playerData.getNetworkProcessor().getKpPing() < 175) {
                            setProbabilty((int) deltaFlying);
                            flag(playerData, "dF=" + deltaFlying);
                        }
                    }else vl = 0;
                }
            }
        }
    }
}
