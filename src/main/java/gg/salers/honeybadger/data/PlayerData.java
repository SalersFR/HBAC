package gg.salers.honeybadger.data;


import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.processor.CombatProcessor;
import gg.salers.honeybadger.processor.MovementProcessor;
import gg.salers.honeybadger.processor.NetworkProcessor;
import gg.salers.honeybadger.processor.RotationProcessor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerData {

    private MovementProcessor movementProcessor;
    private CombatProcessor combatProcessor;
    private NetworkProcessor networkProcessor;
    private RotationProcessor rotationProcessor;
    private UUID uuid;
    private List<Check> checks;

    public PlayerData(UUID uuid) {
        this.movementProcessor = new MovementProcessor(this);
        this.combatProcessor = new CombatProcessor(this);
        this.networkProcessor = new NetworkProcessor(this);
        this.rotationProcessor = new RotationProcessor(this);
        this.uuid = uuid;
        this.checks = new ArrayList<>();
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
