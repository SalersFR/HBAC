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
    private CommandHandler commandHandler;

    @Getter
    private List<Player> alerting = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        loadEvents();
        saveDefaultConfig();
        getCommand("hbac").setExecutor(commandHandler = new CommandHandler());
    }

    @Override
    public void onDisable() {
        alerting.clear();
        commandHandler = null;
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
        new PacketListener();
        BukkitListener bukkitListener = new BukkitListener();
        Bukkit.getPluginManager().registerEvents(bukkitListener, this);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "MC|Brand", bukkitListener);

    }
}
