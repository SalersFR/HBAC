package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import gg.salers.honeybadger.utils.LocationUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementProcessor extends Processor {

    private double deltaX, deltaZ, deltaXZ, deltaY, lastX, lastY, lastZ;
    private PlayerData data ;
    private int airTicks, edgeBlockTicks,ticksSinceHurt;
    private boolean isNearBoat, isInLiquid, isInWeb, isOnClimbable, isAtTheEdgeOfABlock,mathGround;

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
            double x = event.getPacket().getDoubles().read(0);
            deltaX = (x - this.lastX);
            this.lastX = x;

            /**
             * Getting the Y one tick ago
             * And setting the deltaY with the current and last Y
             **/
            double y = event.getPacket().getDoubles().read(1);
            deltaY = (y - this.lastY);
            //Fixing a random bug idfk
            if(deltaY >= 30.0D) deltaY = 0;
            this.lastY = y;

            /**
             * Getting the Z one tick ago
             * And setting the deltaZ with the current and last Z
             **/
            double z = event.getPacket().getDoubles().read(2);
            deltaZ = (z - this.lastZ);
            this.lastZ = z;

            setDeltaXZ(Math.hypot(deltaX, deltaZ));


            /** Getting since how many ticks player is in air **/

            if (LocationUtils.isCloseToGround(event.getPlayer().getLocation())) {
                airTicks = 0;
            } else airTicks++;

            if (LocationUtils.isAtEdgeOfABlock(event.getPlayer())) {
                edgeBlockTicks++;
            } else edgeBlockTicks = 0;


            isNearBoat = LocationUtils.isNearBoat(event.getPlayer());
            isInLiquid = LocationUtils.isInLiquid(event.getPlayer());
            isInWeb = LocationUtils.isCollidingWithWeb(event.getPlayer());
            isOnClimbable = LocationUtils.isCollidingWithClimbable(event.getPlayer());

            this.ticksSinceHurt++;

            this.mathGround = y % 0.015625 == 0;


        }
    }

    @Override
    public void processOut(PacketEvent event) {

    }


    public void handleEDBE() {
        this.ticksSinceHurt = 0;
    }
}
