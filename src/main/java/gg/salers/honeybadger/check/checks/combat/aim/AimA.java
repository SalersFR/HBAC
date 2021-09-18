package gg.salers.honeybadger.check.checks.combat.aim;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;
import io.netty.util.internal.MpscLinkedQueueNode;

@CheckData(name = "Aim (A)", experimental = true)
public class AimA extends Check {

    private double lastDeltaYaw, lastDeltaPitch;
    private int threshold;


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isRot()) {
            double lastDeltaYaw = this.lastDeltaYaw;
            this.lastDeltaYaw = playerData.getRotationProcessor().getDeltaYaw();
            double lastDeltaPitch = this.lastDeltaPitch;
            this.lastDeltaPitch = playerData.getRotationProcessor().getDeltaPitch();
            double yawAccel = Math.abs(playerData.getRotationProcessor().getDeltaYaw() - lastDeltaYaw);

            if(playerData.getRotationProcessor().getDeltaPitch() == 0.00D && yawAccel > 29.5) {
                if(++threshold > 7) {
                    flag(playerData,"yA=" +  (float) yawAccel + " pA=" + (float) playerData.getRotationProcessor().getDeltaPitch());
                }
            } else threshold -= threshold > 0 ? 1 : 0;

        }
    }
}
