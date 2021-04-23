package gg.salers.juaga.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.utils.JPlayerUseAction;

public class JPlayer {
	
	private Player player;
	private UUID uuid;
	private Location from, to;
	private List<Check> checks;
	private JPlayerUseAction useAction;
	private LivingEntity lastTarget;
	
	public Player getPlayer() {
		return player;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public Location getFrom() {
		return from;
	}
	public void setFrom(Location from) {
		this.from = from;
	}
	public Location getTo() {
		return to;
	}
	public void setTo(Location to) {
		this.to = to;
	}
	
	public JPlayer(UUID uuid) {
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);
		this.checks = new ArrayList<>();
		
	}
	
	public List<Check> getChecks() {
		return checks;
	}
	
	public JPlayerUseAction getAction() {
		return useAction;
	}
	
	public void setAction(JPlayerUseAction action) {
		this.useAction = action;
	}

	public LivingEntity getLastTarget() {
		return lastTarget;
	}

	public void setLastTarget(LivingEntity lastTarget) {
		this.lastTarget = lastTarget;
	}
	
	
	
	

}
