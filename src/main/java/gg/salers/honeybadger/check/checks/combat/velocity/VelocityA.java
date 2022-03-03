package gg.salers.honeybadger.check.checks.combat.velocity;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Velocity (A)", experimental = true)
public class VelocityA extends Check {

    /*
    99% first tick vertical?
    need exemptions & stuff
     */


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isMove()){
            double y = playerData.getVelocityProcessor().getVelY();
            int ticks = playerData.getVelocityProcessor().getVelTicks();

            double min = (playerData.getClient().getIntVersion() < 9 ? 0.005 : 0.003);

            double applied = (y / playerData.getMovementProcessor().getDeltaY());

            if (ticks == 1) {
                if(y > min && (applied < 0.99 || Double.isInfinite(applied))) {
                    flag(playerData, "s=" + y + ", c=" + playerData.getMovementProcessor().getDeltaY());
                }
            }
        }
    }
}
