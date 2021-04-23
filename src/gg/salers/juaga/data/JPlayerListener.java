package gg.salers.juaga.data;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JPlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		JPlayerManager.getInstance().jPlayers.put(event.getPlayer().getUniqueId(),
				new JPlayer(event.getPlayer().getUniqueId()));
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		JPlayerManager.getInstance().jPlayers.remove(event.getPlayer().getUniqueId(), 
				JPlayerManager.getInstance().jPlayers.get(event.getPlayer().getUniqueId()));
	}

}
