package gg.salers.honeybadger.check.checks.combat.reach;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.Cuboid;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.PlayerLocation;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
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


        final int range = ping + (ping < 25 ? 125 : 75);

        final List<PlayerLocation> locs = new ArrayList<>();

        //credits to funkemunky
        data.getPlayerLocationList().stream()
                .sorted(Comparator.comparingLong(loc -> Math.abs(loc.getTimestamp() - (System.currentTimeMillis() - ping))))
                .filter(loc -> Math.abs(loc.getTimestamp() - (System.currentTimeMillis() - ping)) < range)
                .forEach(locs::add);
        return locs;
    }

    @SneakyThrows
    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isAttack()) {
            final List<Vector> vectorList = getPastEntitiesLocationsInRange(playerData.getNetworkProcessor()
                    .getKpPing(), playerData).stream().map(loc -> loc.toVec().setY(0)).collect(Collectors.toList());
            final Vector attacker = new Vector(playerData.getMovementProcessor().getX(), 0, playerData.getMovementProcessor().getZ());
            final float distance = (float) vectorList.stream().mapToDouble(vec -> vec.distance(attacker) - 0.3f).min().orElse(0);

            if (distance > 3.1f && distance < 10.f) {
                if (++buffer > 4) {
                    flag(playerData, "d=" + distance);
                }
            } else buffer -= buffer > 0 ? 0.055 : 0;



        }
    }
}
