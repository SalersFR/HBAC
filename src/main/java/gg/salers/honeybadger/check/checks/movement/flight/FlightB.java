package gg.salers.honeybadger.check.checks.movement.flight;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Flight (B)", experimental = true)
public class FlightB extends Check {

    private double lastDeltaY;
    private int threshold;

    @Override
    public void onPacket(Packet packet, PlayerData playerData) {
        if (packet.isFlying()) {
            double predictedDeltaY = (lastDeltaY - 0.08) * 0.9800000190734863D;
            double result = Math.abs(playerData.getMovementProcessor().getDeltaY() - predictedDeltaY);

            handle: {
                if (playerData.getMovementProcessor().getEdgeBlockTicks() > 5) break handle;

                if (playerData.getMovementProcessor().getAirTicks() > 20 && !playerData.getMovementProcessor().isAtTheEdgeOfABlock()) {
                    if (result > 0.01) {
                        if (++threshold > 2) {
                            flag(playerData, "r=" + result);
                        }

                    } else threshold -= threshold > 0 ? 1 : 0;
                }
            }

            this.lastDeltaY = playerData.getMovementProcessor().getDeltaY();
        }
    }
}
