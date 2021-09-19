package gg.salers.honeybadger.data;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckManager;
import gg.salers.honeybadger.processor.impl.CombatProcessor;
import gg.salers.honeybadger.processor.impl.MovementProcessor;
import gg.salers.honeybadger.processor.impl.NetworkProcessor;
import gg.salers.honeybadger.processor.impl.RotationProcessor;
import gg.salers.honeybadger.utils.PlayerLocation;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerData {

    private MovementProcessor movementProcessor;
    private CombatProcessor combatProcessor;
    private NetworkProcessor networkProcessor;
    private RotationProcessor rotationProcessor;
    private CheckManager checkManager;
    private List<PlayerLocation> playerLocationList;



    private UUID uuid;
    private List<Check> checks;

    public PlayerData(UUID uuid) {
        this.movementProcessor = new MovementProcessor(this);
        this.combatProcessor = new CombatProcessor(this);
        this.networkProcessor = new NetworkProcessor(this);
        this.rotationProcessor = new RotationProcessor(this);
        this.checkManager = new CheckManager(this);
        this.uuid = uuid;
        this.checks = this.checkManager.getChecks();
        this.playerLocationList = new ArrayList<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                if(combatProcessor.getAttacked() != null) {
                    final Location loc = combatProcessor.getAttacked().getEyeLocation();
                    playerLocationList.add(new PlayerLocation(loc.getX(),loc.getY(),loc.getZ(),0F,0F));
                    if(playerLocationList.size() >= 20) {
                        playerLocationList.clear();
                    }

                }
            }
        }.runTaskTimer(HoneyBadger.getInstance(),0L,1L);
    }

    /**
     * Getting a bukkit player from a uuid
     *
     * @return the player reliated to the uuid
     */
    public Player getBukkitPlayerFromUUID() {
        return Bukkit.getPlayer(uuid);
    }


}
