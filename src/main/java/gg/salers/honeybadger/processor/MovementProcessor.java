package gg.salers.honeybadger.processor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.utils.LocationUtils;
import lombok.Data;

@Data
public class MovementProcessor {

    private double deltaX,deltaZ,deltaXZ,deltaY;
    private int airTicks,edgeBlockTicks;
    private boolean isNearBoat,isInLiquid,isInWeb,isOnClimbable,isAtTheEdgeOfABlock;
    public MovementProcessor() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),
                PacketType.Play.Client.POSITION,PacketType.Play.Client.FLYING,
                PacketType.Play.Client.POSITION_LOOK,PacketType.Play.Client.LOOK) {
            double lastX,lastY,lastZ;
            @Override
            public void onPacketReceiving(PacketEvent event) {

                /**
                 * Getting the X one tick ago
                 * And setting the deltaX with the current X and last X
                 **/
                double x = event.getPlayer().getLocation().getX();
                deltaZ = (x - this.lastX);
                this.lastX = x;

                /**
                 * Getting the Y one tick ago
                 * And setting the deltaY with the current and last Y
                  **/
                double y = event.getPlayer().getLocation().getY();
                deltaY = (y - this.lastY);
                this.lastY = y;

                /**
                 * Getting the Z one tick ago
                 * And setting the deltaZ with the current and last Z
                 **/
                double z = event.getPlayer().getLocation().getZ();
                deltaZ = (z - this.lastZ);
                this.lastZ = z;

                setDeltaXZ(Math.hypot(deltaX,deltaZ));


                /** Getting since how many ticks player is in air **/

                if(LocationUtils.isCloseToGround(event.getPlayer().getLocation())) {
                   airTicks = 0;
                }else airTicks++;

                if(LocationUtils.isAtEdgeOfABlock(event.getPlayer())) {
                    edgeBlockTicks++;
                }else edgeBlockTicks = 0;


                isNearBoat = LocationUtils.isNearBoat(event.getPlayer());
                isInLiquid = LocationUtils.isInLiquid(event.getPlayer());
                isInWeb = LocationUtils.isCollidingWithWeb(event.getPlayer());
                isOnClimbable = LocationUtils.isCollidingWithClimbable(event.getPlayer());



            }
        });
    }
}
