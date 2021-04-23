package gg.salers.juaga;

import org.bukkit.plugin.java.JavaPlugin;

public class Juaga extends JavaPlugin {
	
	private static Juaga INSTANCE ;
	@Override
	public void onEnable() {
		INSTANCE = this;
	
	}
	
	public static Juaga getInstance() {
		return INSTANCE;
	}
	
	
	
	
 
}
