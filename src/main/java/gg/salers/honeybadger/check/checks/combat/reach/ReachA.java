package gg.salers.honeybadger.check.checks.combat.reach;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
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
    private List<PlayerLocation> getPastEntitiesLocationsInRange(int ping,PlayerData data) {
        List<PlayerLocation> toReturn = new ArrayList<>();
        int range = 0;
        int pingInTicks = ping / 50;
        while (range < 3) {
            toReturn.add(data.getPlayerLocationList().get(pingInTicks + range));
            range++;
        }
        return toReturn;
    }




    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if(packet.isAttack()) {
            List<Cuboid> cuboidList = getPastEntitiesLocationsInRange(playerData.getNetworkProcessor()
                    .getKpPing(),playerData).stream().map(PlayerLocation::hitbox).collect(Collectors.toList());
            Vector attacker = playerData.getBukkitPlayerFromUUID().getLocation().toVector();
            float distance = (float) cuboidList.stream().mapToDouble(cuboid ->
                    cuboid.distanceXZ(attacker.getX(), attacker.getZ()) - 0.3f).min().orElse(0);
            if (distance > 3.1f) {
                if (++buffer > 8) {
                    flag(playerData, "d=" + distance);
                }
            } else buffer -= buffer > 0 ? 1 : 0;

        }
    }
}
