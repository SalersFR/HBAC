package gg.salers.honeybadger.check.checks.combat.autoclicker;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.impl.ClickProcessor;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "AutoClicker (A)", experimental = true)
public class AutoclickerA extends Check {

    private int lastOutliers;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isArmAnimation() && playerData.getClickProcessor().getSamples().size() >= 25) {

            final ClickProcessor clickProcessor = playerData.getClickProcessor();
            final int outliers = clickProcessor.getOutliers();
            final int diff = Math.abs(outliers - lastOutliers);

            if (outliers <= 4 && diff <= 1 && clickProcessor.getDeviation() <= 2.725D) {
                if (++buffer > 2.25)
                    flag(playerData, "diff=" + diff);
            } else if (buffer > 0) buffer -= 0.2D;

            lastOutliers = outliers;
        }

    }

}
