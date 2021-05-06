package gg.salers.juaga.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class JLocation {
	
	private double x,y,z;
	private long time;
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public long getTime() {
		return time;
	}
	public JLocation(double x, double y, double z, long time) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.time = time;
	}
	
	public Location toBukkit(World world) {
		return new Location(world,x,y,z);
	}
	
	public Vector toVector(World world) {
		return toBukkit(world).toVector();
	}
	
	
	
	
	
	

}
