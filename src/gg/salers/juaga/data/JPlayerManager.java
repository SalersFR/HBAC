package gg.salers.juaga.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class JPlayerManager {
	
	public Map<UUID, JPlayer> jPlayers = new HashMap<>();
	private static final JPlayerManager INSTANCE = new JPlayerManager();
	public static JPlayerManager getInstance() {
		return INSTANCE;
	}
	
	public JPlayer getJPlayer(Player player) {
		return jPlayers.get(player.getUniqueId());
	}

}
