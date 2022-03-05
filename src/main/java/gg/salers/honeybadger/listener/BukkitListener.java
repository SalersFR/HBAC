package gg.salers.honeybadger.listener;

import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.ClientVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BukkitListener implements PluginMessageListener, Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //adding the player's data in the cache
        HoneyBadger.getInstance().getPlayerDataManager().add(event.getPlayer());

        //adding the player's channels
        try {
            addChannel(event.getPlayer(),"MC|Brand");
        } catch(Exception e) {
            event.getPlayer().kickPlayer("§cFailed to add channel.");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        //removing the player's data from the cache
        HoneyBadger.getInstance().getPlayerDataManager().remove(event.getPlayer());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        try {
            PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().getPlayerData(player);
            if(data != null) {

                String brand = new String(bytes, "UTF-8");
                if(!brand.isEmpty()) {
                    if(brand.length() > 16) {
                        brand = "custom brand";
                    }

                    if(Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
                        data.setClient(ClientVersion.matchProtocol(com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(player)));
                    } else {
                        Object craftPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        Object connection = craftPlayer.getClass().getField("playerConnection").get(craftPlayer);
                        Object networkManager = connection.getClass().getField("networkManager").get(connection);
                        Field protocol = networkManager.getClass().getField("protocol");
                        protocol.setAccessible(true);
                        data.setClient(ClientVersion.matchProtocol(protocol.getInt(networkManager)));
                    }

                    data.getClient().setBrand(brand);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void addChannel(final Player player, final String channel) {
        try {
            player.getClass().getMethod("addChannel", String.class).invoke(player, channel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }
}
