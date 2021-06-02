package gg.salers.honeybadger.check.checks.movement.speed;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Speed (A)",experimental = true)
public class SpeedA extends Check {

    private double lastDeltaXZ;
    private boolean wasOnGround;
    private int threshold;

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if(event.getPacketType() == PacketType.Play.Client.POSITION || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            double lastDeltaXZ = this.lastDeltaXZ;
            this.lastDeltaXZ = playerData.getMovementProcessor().getDeltaXZ();
            boolean isOnGround = playerData.getBukkitPlayerFromUUID().isOnGround();
            boolean wasOnGround = this.wasOnGround;
            this.wasOnGround = isOnGround;
            double predictedDeltaXZ = lastDeltaXZ * 0.91F;
            double result = (playerData.getMovementProcessor().getDeltaXZ() - predictedDeltaXZ) * 100;
            if(!wasOnGround && !isOnGround) {
                if(result >= 2.61D) {
                    if(++threshold > 5) {
                        setProbabilty((int) result);
                        flag(playerData,"r=" + result);

                    }
                }else threshold -= threshold > 0 ? 1 : 0;


            }
        }

    }
}
