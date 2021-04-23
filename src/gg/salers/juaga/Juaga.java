package gg.salers.juaga;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import gg.salers.juaga.data.JPlayerListener;
import gg.salers.juaga.packets.PacketListener;

public class Juaga extends JavaPlugin {
	
	private static Juaga INSTANCE ;
	@Override
	public void onEnable() {
		INSTANCE = this;
		registerEvents();
		
	
	}
	
	public static Juaga getInstance() {
		return INSTANCE;
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PacketListener(), INSTANCE);
		Bukkit.getPluginManager().registerEvents(new JPlayerListener(), INSTANCE);
	}
	
	
 
}
