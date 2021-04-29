package gg.salers.juaga.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import gg.salers.juaga.Juaga;
import gg.salers.juaga.jplayer.JPlayer;

public class BukkitEvents implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		JPlayer jPlayer = Juaga.getInstance().getPlayerManager().getJPlayer(event.getPlayer());
		jPlayer.setFrom(event.getFrom());
		jPlayer.setTo(event.getTo());
		jPlayer.setDeltaY(event.getTo().getY() - event.getFrom().getY());
		jPlayer.setLastDeltaY(jPlayer.getDeltaY());
	}

}
