package gg.salers.honeybadger;

import gg.salers.honeybadger.check.CheckManager;
import gg.salers.honeybadger.data.PlayerDataManager;
import gg.salers.honeybadger.listener.BukkitListener;
import gg.salers.honeybadger.listener.PacketListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HoneyBadger extends JavaPlugin {

    @Getter
    private static HoneyBadger instance;

    @Override
    public void onEnable() {
        instance = this;
        loadEvents();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        instance = null; //for preventing memory leaks
    }

    /**
     * Getting the instance of PlayerDataManager
     *
     * @return the instance of PlayerDataManager
     **/

    public PlayerDataManager getPlayerDataManager() {
        return PlayerDataManager.getInstance();
    }

    /**
     * Getting the instance of CheckManager
     *
     * @return the instance of CheckManager
     **/

    public CheckManager getCheckManager() {
        return CheckManager.getInstance();
    }

    /**
     * Loading all listeners that we will need
     **/

    private void loadEvents() {
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);
        new PacketListener();
    }
}
