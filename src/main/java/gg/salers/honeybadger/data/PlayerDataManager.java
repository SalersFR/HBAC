package gg.salers.honeybadger.data;


import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    @Getter
    private static PlayerDataManager instance = new PlayerDataManager();

    /** Data cache **/
    private final Map<UUID, PlayerData> uuidPlayerDataMap = new ConcurrentHashMap<>();

    /**
     * Getting a PlayerData from the cache
     *
     * @param uuid the uuid for getting a PlayerData
     * @return a PlayerData from the param uuid
     **/

    public PlayerData getPlayerData(UUID uuid) {
        return this.uuidPlayerDataMap.get(uuid);
    }

    /**
     * Adding a PlayerData to a cache
     *
     * @param uuid the uuid to add in the cache and a new instance of a PlayerData
     */

    public void add(UUID uuid) {
        this.uuidPlayerDataMap.put(uuid,new PlayerData(uuid));
    }

    /**
     * Removing a PlayerData from the cache
     *
     * @param uuid the uuid to remove in the cache and also remove the PlayerData reliated to the uuid
     */

    public void remove(UUID uuid) {
        this.uuidPlayerDataMap.remove(uuid,this.getPlayerData(uuid));
    }




}
