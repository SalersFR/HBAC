package gg.salers.honeybadger.check.checks.combat.autoclicker;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

@CheckData(name = "AutoClicker (A)",experimental = true)
public class AutoclickerA extends Check {

    private boolean isDigging;
    private int ticks,threshold;
    private List<Integer> pastTicksDelay = new ArrayList<>();


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isFlying()) {
            this.isDigging = false;
            ticks++;
        }else if(packet.isDigging()) {
            this.isDigging = false;
            ticks++;
        }else if(packet.isArmAnimation()) {
            if(!this.isDigging) {
                pastTicksDelay.add(ticks);
                double stdDeviation = MathUtils.getStandardDeviation(pastTicksDelay);

                if(pastTicksDelay.size() > 120) {
                    if(stdDeviation < 1.5) {
                        if(++threshold > 1) {
                            setProbabilty((int) stdDeviation);
                            flag(playerData,"sD=" + stdDeviation);
                        }
                    }else threshold -= threshold > 0 ? 1 : 0;
                    pastTicksDelay.clear();
                }
                ticks = 0;
            }
        }
    }
}
