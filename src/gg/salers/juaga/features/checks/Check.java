package gg.salers.juaga.features.checks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import gg.salers.juaga.features.checks.combat.aimassist.AimAssistA;
import gg.salers.juaga.features.checks.combat.aimassist.AimAssistB;
import gg.salers.juaga.features.checks.combat.autoclicker.AutoClickerA;
import gg.salers.juaga.features.checks.combat.killaura.KillAuraA;
import gg.salers.juaga.features.checks.combat.killaura.KillAuraB;
import gg.salers.juaga.features.checks.combat.reach.ReachA;
import gg.salers.juaga.features.checks.move.fly.FlyA;
import gg.salers.juaga.features.checks.move.fly.FlyB;
import gg.salers.juaga.features.checks.move.moveveloctiy.MoveVelocityA;
import gg.salers.juaga.features.checks.move.moveveloctiy.MoveVelocityB;
import gg.salers.juaga.features.checks.move.speed.SpeedA;
import gg.salers.juaga.features.checks.player.badpackets.BadPacketsA;
import gg.salers.juaga.features.checks.player.badpackets.BadPacketsB;
import gg.salers.juaga.features.checks.player.ground.GroundA;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;

public abstract class Check {

	private String name, type;
	private Map<UUID, Integer> vl = new WeakHashMap<>();
	private int delay;
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

	public final List<Class<? extends Check>> checks = Arrays.asList(ReachA.class, FlyA.class, FlyB.class,
			KillAuraA.class, KillAuraB.class, SpeedA.class, MoveVelocityA.class, MoveVelocityB.class, GroundA.class,AimAssistA.class,AimAssistB.class,BadPacketsA.class,BadPacketsB.class);

	protected void fail(JPlayer jplayer) {
		uuid = jplayer.getUuid();
		if (jplayer.getPlayer().getGameMode() == GameMode.CREATIVE
				|| jplayer.getPlayer().getGameMode() == GameMode.SPECTATOR || jplayer.getPlayer().getAllowFlight()
				|| jplayer.getTo().add(-0, -0.1, 0).getBlock().isLiquid()
				|| jplayer.getTo().add(0, 1, 0).getBlock().isLiquid()
				|| jplayer.getTo().add(0, 0, 0).getBlock().isLiquid()
				|| jplayer.getPlayer().getLocation().getBlock().isLiquid())
			return;
		for (Player staff : Bukkit.getOnlinePlayers()) {
			if (staff.hasPermission("juaga.alerts")) {
				delay++;

				vl.putIfAbsent(jplayer.getUuid(), 0);
				if (delay > 8) {
					staff.sendMessage("§6Juaga -> §c " + jplayer.getPlayer().getName() + " §ffailed §c " + name
							+ " §8(§fType" + type + "§8)" + " §8(§fx" + vl.get(jplayer.getUuid()) + "§8)");

					vl.put(jplayer.getUuid(), vl.get(jplayer.getUuid()) + 1);
					delay = 0;
					if (vl.get(jplayer.getUuid()) == 15 || vl.get(jplayer.getUuid()) == 30
							|| vl.get(jplayer.getUuid()) == 45) {
						using();
					}
					if (vl.get(jplayer.getUuid()) > 50) {
						vl.put(uuid, 0);
						String toDispatch = "kick " + jplayer.getPlayer().getName()
								+ " §cYou got kicked for§f:§r §oJUAGA CHEAT DETECTION \n §r§eif you feel this unjustified, please make an appeal on our discord.\n §b§n "
								+ name + " Type [" + type + "]";
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), toDispatch);

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

}
