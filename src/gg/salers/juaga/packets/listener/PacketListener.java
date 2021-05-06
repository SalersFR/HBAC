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
						Entity attacked = event.getPacket().getEntityModifier(jPlayer.getPlayer().getWorld()).read(0);
						jPlayer.setAttacked(attacked);
						if (event.getPacket().getEntityUseActions().read(0) == EntityUseAction.ATTACK) {
							jPlayer.setAction(JPacketUseAction.ATTACK);
						} else if (event.getPacket().getEntityUseActions().read(0) == EntityUseAction.INTERACT) {
							jPlayer.setAction(JPacketUseAction.INTERACT);
						}
						for (Check checks : jPlayer.getChecks()) {
							checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.USE_ENTITY), jPlayer);
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
						jPlayer.pastPositions.add(event.getPlayer().getLocation());
						
					
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
		.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.POSITION_LOOK) {
			public void onPacketReceiving(PacketEvent event) {
				for (Check checks : jPlayer.getChecks()) {
					checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.POSITION_LOOK), jPlayer);
				}
			}
				
		});
		ProtocolLibrary.getProtocolManager()
		.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.LOOK) {
			public void onPacketReceiving(PacketEvent event) {
				for (Check checks : jPlayer.getChecks()) {
					checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.LOOK), jPlayer);
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
		ProtocolLibrary.getProtocolManager()
		.addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Client.KEEP_ALIVE, PacketType.Play.Server.KEEP_ALIVE) {
			public void onPacketReceiving(PacketEvent event) {
				jPlayer.setPing((double) System.currentTimeMillis() - jPlayer.getLastKeepAlive());
				for(Check checks : jPlayer.getChecks()) {
					checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.KEEP_ALIVE), jPlayer);
				}
			
			}
			 @Override
			public void onPacketSending(PacketEvent event) {
				jPlayer.setLastKeepAlive(System.currentTimeMillis());
			}
		});
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Server.TRANSACTION) {
			 @Override
				public void onPacketSending(PacketEvent event) {
					for(Check checks : jPlayer.getChecks()) {
						checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.TRANSACATION), jPlayer);
					}
			 }
		});
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Juaga.getInstance(), PacketType.Play.Server.REL_ENTITY_MOVE) {
			 @Override
				public void onPacketSending(PacketEvent event) {
					for(Check checks : jPlayer.getChecks()) {
						checks.handle(new JPacket(gg.salers.juaga.packets.PacketType.REL_MOVE), jPlayer);
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
			@SuppressWarnings("deprecation")
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
