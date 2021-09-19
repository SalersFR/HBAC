package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RotationProcessor extends Processor {

    private double deltaYaw,deltaPitch,lastPitch,lastYaw,pitch,yaw;
    private PlayerData data;


    public RotationProcessor(PlayerData data) {
       super(data);
    }

    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK) {
            /** Getting yaw one tick ago **/
             yaw = event.getPacket().getFloat().read(0) % 360;
            deltaYaw = yaw - this.lastYaw;
            this.lastYaw = yaw;

            /** Getting pitch one tick ago **/
             pitch = event.getPacket().getFloat().read(1);
            deltaPitch = pitch - this.lastPitch;
            this.lastPitch = pitch;

        }

    }

    @Override
    public void processOut(PacketEvent event) {

    }


}
