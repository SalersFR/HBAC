package gg.salers.honeybadger.check.checks.combat.killaura;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "KillAura (B)", experimental = false)
public class KillAuraB extends Check {

    private int hits;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isAttack()) {
            hits++;
            if (hits > 4) {
                setProbabilty(1);
                flag(playerData, "h=" + hits);
            }

        } else if (packet.isArmAnimation()) {
            hits = 0;
        }
    }
}
