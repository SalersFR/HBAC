package gg.salers.honeybadger.check.checks.combat.reach;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.Cuboid;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.PlayerLocation;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CheckData(name = "Reach (A)", experimental = false)
public class ReachA extends Check {


    /**
     * Getting multiple locations in a range
     *
     * @param ping the attacker's ping
     * @return a list of multiple locations
     */
    private List<PlayerLocation> getPastEntitiesLocationsInRange(int ping, PlayerData data) {
        List<PlayerLocation> toReturn = new ArrayList<>();
        int range = 0;
        int pingInTicks = ping / 50;
        while (range < (pingInTicks + (pingInTicks <= 49 ? 1 : 0))) {
            toReturn.add(data.getPlayerLocationList().get(pingInTicks + range));
            range++;
        }
        return toReturn;
    }

    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isAttack()) {
            final List<Cuboid> cuboidList = getPastEntitiesLocationsInRange(playerData.getNetworkProcessor()
                    .getKpPing(), playerData).stream().map(PlayerLocation::hitbox).collect(Collectors.toList());
            final Vector attacker = new Vector(playerData.getMovementProcessor().getX(), 0, playerData.getMovementProcessor().getZ());
            final float distance = (float) cuboidList.stream().mapToDouble(cuboid ->
                    cuboid.distanceXZ(attacker.getX(), attacker.getZ()) - 0.3f).min().orElse(0);
            if (distance > 3.1f && distance < 10.f) {
                if (++buffer > 4) {
                    flag(playerData, "d=" + distance);
                }
            } else buffer -= buffer > 0 ? 0.05 : 0;

        }
    }
}
