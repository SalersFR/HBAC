package gg.salers.honeybadger.check.checks.misc.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.BlockPosition;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

@CheckData(name = "Protocol (D)", experimental = true)
public class ProtocolD extends Check {

    /*
    Checks if a player is attacking and placing a block in same tick.
     */

    private long currentTicks;

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.getType() == PacketType.Play.Client.BLOCK_PLACE) {
            BlockPosition position = packet.getContainer().getBlockPositionModifier().read(0);
            if (position.getX() != 0 && position.getY() != 0 && position.getZ() != 0 && position.getX() != -1 && position.getY() != -1 && position.getZ() != -1) {
                if(playerData.getTotalTicks() - currentTicks < 1) {
                    if(buffer++ > 5)
                        flag(playerData, "d=" + (playerData.getTotalTicks() - currentTicks));
                } else {
                    buffer -= Math.min(buffer, 0.42);
                }
            }
        } else if (packet.isAttack()) {
            currentTicks = playerData.getTotalTicks();
        }
    }
}
