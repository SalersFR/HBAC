package gg.salers.juaga.packets.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

import gg.salers.juaga.Juaga;
import gg.salers.juaga.features.checks.Check;
import gg.salers.juaga.jplayer.JPlayer;
import gg.salers.juaga.packets.JPacket;
import gg.salers.juaga.packets.JPacketUseAction;

public class PacketListener implements Listener {

	public void initPackets(Player player) {
		JPlayer jPlayer = Juaga.getInstance().getPlayerManager().getJPlayer(player);

		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.USE_ENTITY) {
					@Override
					public void onPacketReceiving(PacketEvent event) {
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.USE_ENTITY), jPlayer);
						}
						Entity attacked = event.getPacket().getEntityModifier(jPlayer.getPlayer().getWorld()).read(0);
						jPlayer.setAttacked(attacked);
						if (event.getPacket().getEntityUseActions().read(0) == EntityUseAction.ATTACK) {
							jPlayer.setAction(JPacketUseAction.ATTACK);
						} else if (event.getPacket().getEntityUseActions().read(0) == EntityUseAction.INTERACT) {
							jPlayer.setAction(JPacketUseAction.INTERACT);
						}
					}

				});

		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.FLYING) {
					@Override
					public void onPacketReceiving(PacketEvent event) {
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.FLYING), jPlayer);
						}
					}
				});
		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.POSITION) {
					public void onPacketReceiving(PacketEvent event) {
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.POSITION), jPlayer);
						}
					}
				});
		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.BLOCK_DIG) {
					public void onPacketReceiving(PacketEvent event) {
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.DIGGING), jPlayer);
						}
					}
				});
		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.ARM_ANIMATION) {
					public void onPacketReceiving(PacketEvent event) {
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.ARM_ANIMATION), jPlayer);
						}
					}
				});
		ProtocolLibrary.getProtocolManager()
				.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.BLOCK_PLACE) {
					public void onPacketReceiving(PacketEvent event) {
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.BLOCK_PLACE), jPlayer);
						}
					}
				});
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws InstantiationException, IllegalAccessException {
		Juaga.getInstance().getPlayerManager().jPlayers.put(event.getPlayer().getUniqueId(),
				new JPlayer(event.getPlayer().getUniqueId()));
		JPlayer jPlayer = Juaga.getInstance().getPlayerManager().getJPlayer(event.getPlayer());

		initPackets(event.getPlayer());
		for (Class<? extends Check> clazz : Check.INSTANCE.checks) {
			Check instance = clazz.newInstance();
			jPlayer.getChecks().add(instance);

		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		JPlayer jPlayer = Juaga.getInstance().getPlayerManager().getJPlayer(event.getPlayer());
		Juaga.getInstance().getPlayerManager().jPlayers.remove(event.getPlayer().getUniqueId(), jPlayer);
	}

}
