package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import gg.salers.honeybadger.utils.PlayerLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementProcessor extends Processor {

    private double deltaX, deltaZ, deltaXZ, deltaY, lastX, lastY, lastZ, x, y, z, lastDeltaX, lastDeltaY, lastDeltaZ, lastDeltaXZ;
    private PlayerLocation currentLocation, lastLocation;
    private PlayerData data;

    public MovementProcessor(PlayerData data) {
        super(data);

    }

    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION) {


            /**
             * Getting the X one tick ago
             * And setting the deltaX with the current X and last X
             **/

            this.lastX = x;
            x = event.getPacket().getDoubles().read(0);

            lastDeltaX = deltaX;
            if ((x - this.lastX) < 30)
                deltaX = (x - this.lastX);
            else deltaX = 0;

            /**
             * Getting the Y one tick ago
             * And setting the deltaY with the current and last Y
             **/

            this.lastY = y;
            y = event.getPacket().getDoubles().read(1);

            lastDeltaY = deltaY;
            if ((y - this.lastY) < 30)
                deltaY = (y - this.lastY);
            else deltaY = 0;


            /**
             * Getting the Z one tick ago
             * And setting the deltaZ with the current and last Z
             **/

            this.lastZ = z;
            z = event.getPacket().getDoubles().read(2);

            lastDeltaZ = deltaZ;
            if ((z - this.lastZ) < 30)
                deltaZ = (z - this.lastZ);
            else deltaZ = 0;

            //xz kek
            lastDeltaXZ = deltaXZ;
            deltaXZ = Math.hypot(deltaX, deltaZ);

            lastLocation = currentLocation;

            if(event.getPacketType() == PacketType.Play.Client.POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK) {
                currentLocation = new PlayerLocation(x, y, z, event.getPacket().getFloat().read(0), event.getPacket().getFloat().read(1));
            } else {
                currentLocation = new PlayerLocation(x, y, z, event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch());
            }

        }
    }

    @Override
    public void processOut(PacketEvent event) {

    }

}
