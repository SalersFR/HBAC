package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "KillAura (A)", experimental = false)
public class KillAuraA extends Check {


    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {

        if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (playerData.getCombatProcessor().getAction() == EnumWrappers.EntityUseAction.ATTACK) {
                long deltaFlying = Math.abs(System.currentTimeMillis() - playerData.getNetworkProcessor().getLastFlying());
                if (deltaFlying< 10L) {
                    if (playerData.getNetworkProcessor().getKpPing() < 175) {
                        setProbabilty((int) deltaFlying);
                        flag(playerData, "dF=" + deltaFlying);
                    }
                }
            }
        }

    }
}
