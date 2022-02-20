package gg.salers.honeybadger.listener;

import gg.salers.honeybadger.HoneyBadger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //adding the player's data in the cache
        HoneyBadger.getInstance().getPlayerDataManager().add(event.getPlayer());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        //removing the player's data from the cache
        HoneyBadger.getInstance().getPlayerDataManager().remove(event.getPlayer());
    }

}
