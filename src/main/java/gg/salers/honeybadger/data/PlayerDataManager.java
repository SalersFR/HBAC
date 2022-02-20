package gg.salers.honeybadger.data;


import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    @Getter
    private static PlayerDataManager instance = new PlayerDataManager();

    /**
     * Data cache
     **/
    private final Map<Player, PlayerData> playerPlayerDataMap = new ConcurrentHashMap<>();

    /**
     * Getting a PlayerData from the cache
     *
     * @param player the player for getting a PlayerData
     * @return a PlayerData from the param player
     **/

    public PlayerData getPlayerData(Player player) {
        return this.playerPlayerDataMap.get(player);
    }

    /**
     * Adding a PlayerData to a cache
     *
     * @param player the player to add in the cache and a new instance of a PlayerData
     */

    public void add(Player player) {
        this.playerPlayerDataMap.put(player, new PlayerData(player));
    }

    /**
     * Removing a PlayerData from the cache
     *
     * @param player the player to remove in the cache and also remove the PlayerData reliated to the player
     */

    public void remove(Player player) {
        this.playerPlayerDataMap.remove(player, this.getPlayerData(player));
    }


}
