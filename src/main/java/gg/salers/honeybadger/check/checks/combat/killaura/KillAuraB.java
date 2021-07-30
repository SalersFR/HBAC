package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "KillAura (B)", experimental = false)
public class KillAuraB extends Check {

    private int hits;

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (playerData.getCombatProcessor().getAction() == EnumWrappers.EntityUseAction.ATTACK) {
                if (++hits > 4) {
                    setProbabilty(1);
                    flag(playerData, "h=" + hits);
                }
            }
        } else if (event.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            hits = 0;
        }
    }
}
