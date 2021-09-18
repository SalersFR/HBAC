package gg.salers.honeybadger.listener;

import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.CheckManager;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws InstantiationException, IllegalAccessException {
        //adding the player's data in the cache

        HoneyBadger.getInstance().getPlayerDataManager().add(event.getPlayer().getUniqueId());


    }



    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        //removing the player's data from the cache
        HoneyBadger.getInstance().getPlayerDataManager().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onEDBE(EntityDamageByEntityEvent event) {
        if(event.getEntity().getType() == EntityType.PLAYER) {
            final PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().getPlayerData(event.getEntity().getUniqueId());
            if(data == null) return;

            data.getMovementProcessor().handleEDBE();
        }
    }
}
