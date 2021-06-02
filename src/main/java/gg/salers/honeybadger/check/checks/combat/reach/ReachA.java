package gg.salers.honeybadger.check.checks.combat.reach;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.Cuboid;
import gg.salers.honeybadger.utils.PlayerLocation;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CheckData(name = "Reach (A)", experimental = false)
public class ReachA extends Check {

    private List<PlayerLocation> pastEntitiesLocations = new ArrayList<>();
    private int threshold;

    /**
     * Getting multiple locations in a range
     *
     * @param ping the attacker's ping
     * @return a list of multiple locations
     */
    private List<PlayerLocation> getPastEntitiesLocationsInRange(int ping) {
        List<PlayerLocation> toReturn = new ArrayList<>();
        int range = 0;
        int pingInTicks = ping / 50;
        while (range < 3) {
            toReturn.add(this.pastEntitiesLocations.get(pingInTicks + range));
            range++;
        }
        return toReturn;
    }

    @Override
    public void onPacket(PacketEvent event, PlayerData playerData) {
        if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            if (playerData.getCombatProcessor().getAction() == EnumWrappers.EntityUseAction.ATTACK) {
                List<Cuboid> cuboidList = getPastEntitiesLocationsInRange(playerData.getNetworkProcessor()
                        .getKpPing()).stream().map(PlayerLocation::hitbox).collect(Collectors.toList());
                Vector attacker = playerData.getBukkitPlayerFromUUID().getLocation().toVector();
                float distance = (float) cuboidList.stream().mapToDouble(cuboid ->
                        cuboid.distanceXZ(attacker.getX(), attacker.getZ()) - 0.1f).min().orElse(0);
                if (distance > 3.1f) {
                    if (++threshold > 8) {
                        flag(playerData, "d=" + distance);
                    }
                } else threshold -= threshold > 0 ? 1 : 0;


            }
        } else if (event.getPacketType() == PacketType.Play.Server.REL_ENTITY_MOVE) {
            if (playerData.getCombatProcessor().getAttacked() != null) {
                if (pastEntitiesLocations.size() > 19) {
                    pastEntitiesLocations.clear();
                }
                pastEntitiesLocations.add(new PlayerLocation(playerData.getCombatProcessor().getAttacked().getLocation()
                        .getX(), playerData.getCombatProcessor().getAttacked().getLocation().getY(),
                        playerData.getCombatProcessor().getAttacked().getLocation().getZ(),
                        playerData.getCombatProcessor().getAttacked().getLocation().getYaw()
                        , playerData.getCombatProcessor().getAttacked().getLocation().getPitch()));

            }
        }

    }
}
