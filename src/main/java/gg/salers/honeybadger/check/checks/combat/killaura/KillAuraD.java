package gg.salers.honeybadger.check.checks.combat.killaura;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "KillAura (D)", experimental = true)
public class KillAuraD extends Check {

    private int ticks;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isAttack()) {

            if (playerData.getCombatProcessor().getAttacked().getEntityId() != playerData.getCombatProcessor().getLastAttacked().getEntityId()) {
                if (++ticks > 1) {
                    flag(playerData, "t=" + ticks);
                }
            }
        } else if (packet.isFlying()) {
            ticks = 0;
        }
    }
}
