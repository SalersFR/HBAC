package gg.salers.honeybadger.processor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import lombok.Data;

@Data
public class RotationProcessor {

    private double deltaYaw,deltaPitch;

    public RotationProcessor() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),
               PacketType.Play.Client.LOOK) {
            double lastPitch,lastYaw;
            @Override
            public void onPacketReceiving(PacketEvent event) {
                /** Getting yaw one tick ago **/
                double yaw = event.getPlayer().getLocation().getYaw();
                deltaYaw = yaw - this.lastYaw;
                this.lastYaw = yaw;

                /** Getting pitch one tick ago **/
                double pitch = event.getPlayer().getLocation().getYaw();
                deltaPitch = pitch - this.lastPitch;
                this.lastPitch = pitch;

            }
        });
    }
}
