package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;
import org.bukkit.Location;

@CheckData(name = "KillAura (C)",experimental = true)
public class KillAuraC extends Check {

    private int armAnimations,attacks,threshold;
    private Location lastVictimLocation;

    /**
   got the idea from GladUrBad
     */


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isArmAnimation()) {
            Location lastVictimLocation = this.lastVictimLocation;
            this.lastVictimLocation = playerData.getCombatProcessor().getAttacked().getLocation();
            if(++armAnimations >= 100) {
                if(playerData.getCombatProcessor().getAttacked().getEntityId() == playerData.getCombatProcessor().
                        getLastAttacked().getEntityId() &&lastVictimLocation.getPitch() != playerData.getCombatProcessor().
                        getLastAttacked().getLocation().getPitch() ) {
                    if(attacks >= 80) {
                        if(++threshold > 2) {
                            flag(playerData,"a=" +attacks);
                        }
                    }else threshold -= threshold > 0 ? 0.5 : 0;
                }

                attacks = armAnimations = 0;
            }

        }else if(packet.isAttack()) {
            attacks++;


        }
    }
}
