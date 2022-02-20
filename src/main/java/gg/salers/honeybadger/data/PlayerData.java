package gg.salers.honeybadger.data;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckManager;
import gg.salers.honeybadger.processor.Processor;
import gg.salers.honeybadger.processor.impl.*;
import gg.salers.honeybadger.utils.PlayerLocation;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerData {

    private final MovementProcessor movementProcessor;
    private final CombatProcessor combatProcessor;
    private final NetworkProcessor networkProcessor;
    private final RotationProcessor rotationProcessor;
    private final CollisionProcessor collisionProcessor;
    private final ClickProcessor clickProcessor;
    private final VelocityProcessor velocityProcessor;
    private final CheckManager checkManager;
    private final List<PlayerLocation> playerLocationList;
    private final List<Processor> processors = new ArrayList<>();
    private Check debugging;
    private final Player player;


    private List<Check> checks;

    public PlayerData(Player player) {
        this.movementProcessor = new MovementProcessor(this);
        this.combatProcessor = new CombatProcessor(this);
        this.networkProcessor = new NetworkProcessor(this);
        this.rotationProcessor = new RotationProcessor(this);
        this.collisionProcessor = new CollisionProcessor(this);
        this.velocityProcessor = new VelocityProcessor(this);
        this.clickProcessor = new ClickProcessor(this);
        this.checkManager = new CheckManager(this);
        this.player = player;
        this.checks = this.checkManager.getChecks();
        this.playerLocationList = new ArrayList<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (combatProcessor.getAttacked() != null) {
                    final Location loc = combatProcessor.getAttacked().getEyeLocation();
                    playerLocationList.add(new PlayerLocation(loc.getX(), loc.getY(), loc.getZ(), 0F, 0F));
                    if (playerLocationList.size() >= 20) {
                        playerLocationList.clear();
                    }

                }
            }
        }.runTaskTimer(HoneyBadger.getInstance(), 0L, 1L);
    }

    /**
     * Getting a bukkit player from a uuid
     *
     * @return the player reliated to the uuid
     */
    public Player getBukkitPlayerFromUUID() {
        return player;
    }


}
