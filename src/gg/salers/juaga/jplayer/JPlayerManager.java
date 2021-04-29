package gg.salers.juaga.jplayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class JPlayerManager {
	
	public static JPlayerManager INSTANCE = new JPlayerManager();
	
	public Map<UUID,JPlayer> jPlayers = new HashMap<>();
	
	public JPlayer getJPlayer(Player player) {
		return jPlayers.get(player.getUniqueId());
	}
	

}
