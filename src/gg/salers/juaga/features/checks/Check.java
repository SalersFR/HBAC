package gg.salers.juaga.features.checks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import gg.salers.juaga.Juaga;
import gg.salers.juaga.features.checks.combat.aimassist.AimAssistA;
import gg.salers.juaga.features.checks.combat.aimassist.AimAssistB;
import gg.salers.juaga.features.checks.combat.killaura.KillAuraA;
import gg.salers.juaga.features.checks.combat.killaura.KillAuraB;
import gg.salers.juaga.features.checks.combat.killaura.KillAuraC;
import gg.salers.juaga.features.checks.combat.reach.ReachA;
import gg.salers.juaga.features.checks.move.fly.FlyA;
import gg.salers.juaga.features.checks.move.fly.FlyB;
import gg.salers.juaga.features.checks.move.fly.FlyC;
import gg.salers.juaga.features.checks.move.moveveloctiy.MoveVelocityA;
import gg.salers.juaga.features.checks.move.moveveloctiy.MoveVelocityB;
import gg.salers.juaga.features.checks.move.moveveloctiy.MoveVelocityC;
import gg.salers.juaga.features.checks.move.speed.SpeedA;
import gg.salers.juaga.features.checks.move.speed.SpeedB;
import gg.salers.juaga.features.checks.player.badpackets.BadPacketsA;
import gg.salers.juaga.features.checks.player.badpackets.BadPacketsB;
import gg.salers.juaga.features.checks.player.ground.GroundA;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;

public abstract class Check {

	private String name, type;
	private Map<UUID, Integer> vl = new WeakHashMap<>();
	private int delay,thresholdToLagBack;
	private UUID uuid;
	

	public static Check INSTANCE = new Check() {

		@Override
		public void handle(JPacket jPacket, JPlayer jplayer) {
			// TODO Auto-generated method stub

		}

	};

	public Check(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public Check() {
	}

	public abstract void handle(JPacket jPacket, JPlayer jplayer);

	public final List<Class<? extends Check>> checks = Arrays.asList(ReachA.class, FlyA.class, FlyB.class, FlyC.class,
			KillAuraA.class, KillAuraB.class, KillAuraC.class, SpeedA.class, SpeedB.class, MoveVelocityA.class,
			MoveVelocityB.class, MoveVelocityC.class, GroundA.class, AimAssistA.class, AimAssistB.class,
			BadPacketsA.class, BadPacketsB.class);

	protected void fail(JPlayer jplayer, String moreInfo) {
		uuid = jplayer.getUuid();
		if (jplayer.getPlayer().getGameMode() == GameMode.CREATIVE
				|| jplayer.getPlayer().getGameMode() == GameMode.SPECTATOR || jplayer.getPlayer().getAllowFlight()
				|| jplayer.getTo().add(-0, -0.1, 0).getBlock().isLiquid()
				|| jplayer.getTo().add(0, 1, 0).getBlock().isLiquid()
				|| jplayer.getTo().add(0, 0, 0).getBlock().isLiquid()
				|| jplayer.getTo().add(0, -1, 0).getBlock().isLiquid()
				|| jplayer.getPlayer().getLocation().getBlock().isLiquid()
			   )
			return;
		for (Player staff : Bukkit.getOnlinePlayers()) {
			if (staff.hasPermission("juaga.alerts")) {
				delay++;

				vl.putIfAbsent(jplayer.getUuid(), 0);
				if (delay > 8) {
					staff.sendMessage("§f[§6Juaga§f]§7 " + " §e" + jplayer.getPlayer().getName() + " §7failed §e" + name
							+ " §7type: §e" + type + " §7info: " + moreInfo + " §cvl: " + vl.get(jplayer.getUuid()));

					vl.put(jplayer.getUuid(), vl.get(jplayer.getUuid()) + 1);
					delay = 0;
					if (vl.get(jplayer.getUuid()) == 15 || vl.get(jplayer.getUuid()) == 30
							|| vl.get(jplayer.getUuid()) == 45) {
						using();
					}
					if (vl.get(jplayer.getUuid()) > 50) {
						vl.put(uuid, 0);
						new BukkitRunnable() {

							@Override
							public void run() {
								String toDispatch = Juaga.getInstance().getConfig()
										.getString("juaga.punishement-command").replace("&", "§")
										.replace("%player%", jplayer.getPlayer().getName()).replace("%cheat%", name)
										.replace("%type%", type);
								if (toDispatch == null)
									toDispatch = "kick " + jplayer.getPlayer().getName()
											+ " §cYou got kicked for§f:§r §oJUAGA CHEAT DETECTION \n §r§eif you feel this unjustified, please make an appeal on our discord.\n §b§n "
											+ name + " Type [" + type + "]";
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), toDispatch);

							}
						}.runTaskLater(Juaga.getInstance(), 2L);

					}
				}
			}

		}

	}

	protected void using() {
		Player player = Bukkit.getPlayer(uuid);
		if (player.getPlayer().getGameMode() == GameMode.CREATIVE
				|| player.getPlayer().getGameMode() == GameMode.SPECTATOR || player.getPlayer().getAllowFlight())
			return;
		for (Player staff : Bukkit.getOnlinePlayers()) {
			if (staff.hasPermission("juaga.alerts")) {
				staff.sendMessage("§d§kS §c§lJuaga > §6 " + player.getPlayer().getName() + " §fis suspected of §6 "
						+ name + " §d§kS");
			}
		}

	}

	protected void debug(Object obj) {
		Bukkit.broadcastMessage("§bDEBUG:§f " + obj);
	}

	public void setType(String type) {
		this.type = type;
	}
	
	protected void lagBack(JPlayer jPlayer) {

		
		
		if(++thresholdToLagBack > 3) {
		jPlayer.setHasToLagBack(true);
		jPlayer.setLastLagBack(System.currentTimeMillis());
		thresholdToLagBack = 0;
		}
	}

}
