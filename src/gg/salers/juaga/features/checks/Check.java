package gg.salers.juaga.features.checks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gg.salers.juaga.features.checks.combat.reach.ReachA;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;

public abstract class Check {
	
	public static Check INSTANCE = new Check() {

		@Override
		public void handle(JPacket jPacket, JPlayer jplayer) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public abstract void handle(JPacket jPacket, JPlayer jplayer);
	
	public final  List<Class<? extends Check>> checks = Arrays.asList(ReachA.class);
	
	protected void fail(JPlayer player,String name) {
		for(Player staff : Bukkit.getOnlinePlayers()) {
			if(staff.hasPermission("juaga.alerts")) {
			staff.sendMessage("§cJuaga §7-> §c " + player.getPlayer().getName() + " §7is verbosing §c" + name );
			}
			
		}
		
	}
	

}
