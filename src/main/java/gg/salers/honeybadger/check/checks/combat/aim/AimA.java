package gg.salers.honeybadger.check.checks.combat.aim;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Aim (A)", experimental = true)
public class AimA extends Check {

    private double lastDeltaYaw, lastDeltaPitch;
    private int threshold;

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            double lastDeltaYaw = this.lastDeltaYaw;
            this.lastDeltaYaw = playerData.getRotationProcessor().getDeltaYaw();
            double lastDeltaPitch = this.lastDeltaPitch;
            this.lastDeltaPitch = playerData.getRotationProcessor().getDeltaPitch();
            double yawAccel = Math.abs(playerData.getRotationProcessor().getDeltaYaw() - lastDeltaYaw);
            double pitchAccel = Math.abs(playerData.getRotationProcessor().getDeltaPitch() - lastDeltaPitch);
            if(pitchAccel < 0.0001 && yawAccel > 29.5) {
                if(++threshold > 12) {
                    flag(playerData,"yA=" +  (float) yawAccel + " pA=" + (float)pitchAccel );
                }
            } else threshold -= threshold > 0 ? 1 : 0;

        }
    }
}
