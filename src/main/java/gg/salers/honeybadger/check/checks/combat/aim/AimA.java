package gg.salers.honeybadger.check.checks.combat.aim;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Aim (A)", experimental = true)
public class AimA extends Check {

    private double lastDeltaYaw, lastDeltaPitch;
    private int threshold;

    @Override
    public void onPacket(final Packet packet, final PlayerData playerData) {
        if (packet.isRotation()) {
            double yawAccel = Math.abs(playerData.getRotationProcessor().getDeltaYaw() - lastDeltaYaw);
            double pitchAccel = Math.abs(playerData.getRotationProcessor().getDeltaPitch() - lastDeltaPitch);

            if (pitchAccel == 0.0 && yawAccel > 29.5) {
                if (++threshold > 7) {
                    flag(playerData, "yA=" + (float) yawAccel + " pA=" + (float) pitchAccel);
                }
            } else threshold -= threshold > 0 ? 1 : 0;

            this.lastDeltaYaw = playerData.getRotationProcessor().getDeltaYaw();
            this.lastDeltaPitch = playerData.getRotationProcessor().getDeltaPitch();
        }
    }
}
