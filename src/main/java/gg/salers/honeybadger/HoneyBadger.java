package gg.salers.honeybadger;

import gg.salers.honeybadger.command.CommandHandler;
import gg.salers.honeybadger.data.PlayerDataManager;
import gg.salers.honeybadger.listener.BukkitListener;
import gg.salers.honeybadger.listener.PacketListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class HoneyBadger extends JavaPlugin {

    @Getter
    private static HoneyBadger instance;

    @Getter
    private List<Player> alerting = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        loadEvents();
        saveDefaultConfig();
        getCommand("hbac").setExecutor(new CommandHandler());
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
     * Loading all listeners that we will need
     **/

    private void loadEvents() {
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);
        new PacketListener();
    }
}
