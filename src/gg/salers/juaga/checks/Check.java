package gg.salers.juaga.checks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.packets.JPacket;

public abstract class Check {

	protected String name;
	private static Check INSTANCE = new Check("check") {
		@Override
		public void handle(JPacket packet, JPlayer jPlayer) {

		}
	};
	
	
	protected Check(String name) {
		this.name = name;
	}

	public abstract void handle(JPacket packet, JPlayer jPlayer);

	public static Check getInstance() {
		return INSTANCE;
	}

	protected void fail(JPlayer jPlayer) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if(player.hasPermission("ajaj.alerts")) {
				player.sendMessage("&5AntiJAJ §f>&d " + jPlayer.getPlayer().getName() + " §7has failed §d" + name);
				
			}

		}
	}
}
