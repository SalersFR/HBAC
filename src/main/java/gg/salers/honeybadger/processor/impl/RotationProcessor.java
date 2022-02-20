package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import gg.salers.honeybadger.utils.MathUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RotationProcessor extends Processor {


    private PlayerData data;
    private float yaw, pitch,
            deltaYaw, deltaPitch,
            lastYaw, lastPitch,
            lastDeltaYaw, lastDeltaPitch,
            yawAccel, pitchAccel,
            lastYawAccel, lastPitchAccel;
    private double gcdYaw, gcdPitch, absGcdPitch, absGcdYaw;
    private long expandedGcdYaw, expandedGcdPitch;


    public RotationProcessor(PlayerData data) {
        super(data);
    }

    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK) {

            //last tick
            this.lastYaw = yaw;
            this.lastPitch = pitch;

            //current tick
            yaw = event.getPacket().getFloat().read(0) % 360;
            pitch = event.getPacket().getFloat().read(1);

            deltaYaw = Math.abs(yaw - lastYaw) % 360F;
            deltaPitch = Math.abs(pitch - lastPitch);

            lastYawAccel = this.yawAccel;
            lastPitchAccel = this.pitchAccel;

            yawAccel = Math.abs(deltaYaw - lastDeltaYaw);
            pitchAccel = Math.abs(deltaPitch - lastDeltaPitch);

            gcdYaw = MathUtils.getGcd(deltaYaw, lastDeltaYaw);
            gcdPitch = MathUtils.getGcd(deltaPitch, lastDeltaPitch);

            absGcdYaw = MathUtils.getGcd(Math.abs(deltaYaw), Math.abs(lastDeltaYaw));
            absGcdPitch = MathUtils.getGcd(Math.abs(deltaPitch), Math.abs(lastDeltaPitch));

            expandedGcdYaw = (long) MathUtils.gcd(0x4000, (Math.abs(deltaYaw) * MathUtils.EXPANDER), (Math.abs(lastDeltaYaw) * MathUtils.EXPANDER));
            expandedGcdPitch = (long) MathUtils.gcd(0x4000, (Math.abs(deltaPitch) * MathUtils.EXPANDER), (Math.abs(lastDeltaPitch) * MathUtils.EXPANDER));


        }

    }

    @Override
    public void processOut(PacketEvent event) {

    }


}
