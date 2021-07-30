package gg.salers.honeybadger.check.checks.movement.flight;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "Flight (B)", experimental = true)
public class FlightB extends Check {

    private double lastDeltaY, lastResult;
    private int threshold;

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.POSITION
                || event.getPacketType() == PacketType.Play.Client.POSITION_LOOK) {
            double predictedDeltaY = (this.lastDeltaY - 0.08) * 0.9800000190734863D;
            double result = Math.abs(playerData.getMovementProcessor().getDeltaY() - predictedDeltaY);

            handle: {
                if (playerData.getMovementProcessor().getEdgeBlockTicks() > 5) break handle;

                if (playerData.getMovementProcessor().getAirTicks() > 20 && !playerData.getMovementProcessor().isAtTheEdgeOfABlock()) {
                    if (result > 0.085 || (result == 0.0784000015258789 && lastResult == 0.0784000015258789)) {
                        if (++threshold > 8) {
                            flag(playerData, "r=" + result + " lR=" + lastResult);
                        }

                    } else threshold -= threshold > 0 ? 1 : 0;
                }
            }

            this.lastDeltaY = playerData.getMovementProcessor().getDeltaY();
            this.lastResult = result;
        }
    }
}
