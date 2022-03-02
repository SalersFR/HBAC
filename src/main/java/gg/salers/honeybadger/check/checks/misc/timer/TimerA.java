package gg.salers.honeybadger.check.checks.misc.timer;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

/**
 * @author Salers
 * Made the 20/02/2022
 */

@CheckData(name = "Timer (A)", experimental = true)
public class TimerA extends Check {

    private double avgElapsed = 50;
    private long lastSent;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isFlying()) {

            final long elapsed = System.currentTimeMillis() - lastSent;
            avgElapsed = ((avgElapsed * 14) + elapsed) / 15;

            //thresholds
            final double slowThreshold = 51.25 + playerData.getNetworkProcessor().getKpPing() / 75D;
            final double fastThreshold = 49.25 - playerData.getNetworkProcessor().getKpPing() / 50D;

            final boolean slower = slowThreshold < avgElapsed;
            final boolean faster = fastThreshold > avgElapsed;

            final String flag = faster ? "faster" : "slower";

            if(playerData.getTotalTicks() > 100 && (slower || faster) && avgElapsed < 1000000) {
                if(++buffer > 7.5) {
                    flag(playerData, flag + ", avg=" + avgElapsed);
                    avgElapsed = 50;
                }
            } else if(buffer > 0) buffer -= 0.5D;

            lastSent = System.currentTimeMillis();

        }
    }
}
