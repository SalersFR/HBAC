package gg.salers.juaga.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
	private List<Location> pastLocations = new LinkedList<>();

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

	public int getPing() {
		return ((CraftPlayer) player).getHandle().ping;
	}

	public JPlayerUseAction getUseAction() {
		return useAction;
	}

	public List<Location> getPastLocations() {
		return pastLocations;
	}

	public boolean isExemptedMove() {
		return player.getAllowFlight() || player.isFlying() || player.getGameMode() == GameMode.CREATIVE
				|| player.getGameMode() == GameMode.SPECTATOR
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.PISTON_BASE
				|| player.getLocation().add(2, 2, 2).getBlock().getType() == Material.PISTON_EXTENSION
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.PISTON_MOVING_PIECE
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.PISTON_STICKY_BASE
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.SLIME_BLOCK
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.LADDER
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.VINE
				|| player.getLocation().add(2, -2, 2).getBlock().getType() == Material.WEB
				|| player.getVehicle() != null;

	}

}
