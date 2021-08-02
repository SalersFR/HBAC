package gg.salers.honeybadger.processor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Data;

@Data
public class RotationProcessor {

    private double deltaYaw,deltaPitch, lastPitch,lastYaw;

    private PlayerData data;

    public RotationProcessor(PlayerData data) {
       this.data = data;
    }

    public void handle(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            /** Getting yaw one tick ago **/
            double yaw = event.getPlayer().getLocation().getYaw();
            deltaYaw = yaw - this.lastYaw;
            this.lastYaw = yaw;

            /** Getting pitch one tick ago **/
            double pitch = event.getPlayer().getLocation().getPitch();
            deltaPitch = pitch - this.lastPitch;
            this.lastPitch = pitch;
        }
    }
}
