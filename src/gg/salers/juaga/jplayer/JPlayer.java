package gg.salers.juaga.jplayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.packets.JPacketUseAction;

public class JPlayer {

	private Location from, to,past,pastPast;
	private Player player;
	private UUID uuid;
	private JPacketUseAction action;
	private Entity attacked;
	private double lastDeltaY, deltaY,ping;
	public List<Location> pastPositions = new LinkedList<>();
	public double getPing() {
		return ping;
	}

	public void setPing(double ping) {
		this.ping = ping;
	}

	private long lastKeepAlive;

	private List<Check> checks = new ArrayList<>();

	public JPlayer(UUID uuid) {
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);

	}

	public List<Check> getChecks() {
		return checks;
	}

	public void setChecks(List<Check> checks) {
		this.checks = checks;
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
	}

	public Player getPlayer() {
		return player;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setFrom(Location from) {
		this.from = from;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public JPacketUseAction getAction() {
		return action;
	}

	public void setAction(JPacketUseAction action) {
		this.action = action;
	}

	public Entity getAttacked() {
		return attacked;
	}

	public void setAttacked(Entity attacked) {
		this.attacked = attacked;
	}

	public double getLastDeltaY() {
		return lastDeltaY;
	}

	public void setLastDeltaY(double lastDeltaY) {
		this.lastDeltaY = lastDeltaY;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}

	public long getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(long lastKeepAlive) {
		this.lastKeepAlive = lastKeepAlive;
	}

	public Location getPast() {
		return past;
	}

	public Location getPastPast() {
		return pastPast;
	}

	public void setPast(Location past) {
		this.past = past;
	}

	public void setPastPast(Location pastPast) {
		this.pastPast = pastPast;
	}
	
	

	
}
