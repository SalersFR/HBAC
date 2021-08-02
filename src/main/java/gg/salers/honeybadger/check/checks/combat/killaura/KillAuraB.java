package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "KillAura (B)", experimental = false)
public class KillAuraB extends Check {

    private int hits;

    @Override
    public void onPacket(Packet packet, PlayerData playerData) {
        if (packet.isAttack()) {

            if (++hits > 4) {
                setProbabilty(1);
                flag(playerData, "h=" + hits);
            }
        } else if (packet.isArmAnimation()) {
            hits = 0;

        }
    }
}
