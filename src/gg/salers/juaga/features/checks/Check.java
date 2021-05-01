package gg.salers.juaga.features.checks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gg.salers.juaga.features.checks.combat.autoclicker.AutoClickerA;
import gg.salers.juaga.features.checks.combat.killaura.KillAuraA;
import gg.salers.juaga.features.checks.combat.reach.ReachA;
import gg.salers.juaga.features.checks.move.fly.FlyA;
import gg.salers.juaga.features.checks.move.fly.FlyB;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;

public abstract class Check {
	
	
	private String name;
	public static Check INSTANCE = new Check() {

		@Override
		public void handle(JPacket jPacket, JPlayer jplayer) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public Check(String name) {
		this.name = name;
	}
	
	public Check() {}
	
	public abstract void handle(JPacket jPacket, JPlayer jplayer);
	
	public final  List<Class<? extends Check>> checks = Arrays.asList(ReachA.class,FlyA.class,FlyB.class, KillAuraA.class,AutoClickerA.class);
	
	protected void fail(JPlayer player) {
		for(Player staff : Bukkit.getOnlinePlayers()) {
			if(staff.hasPermission("juaga.alerts")) {
			staff.sendMessage("§cJuaga §7-> §c " + player.getPlayer().getName() + " §7is verbosing §c" + name );
			}
			
		}
		
	}
	

}
