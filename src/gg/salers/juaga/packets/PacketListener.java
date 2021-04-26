package gg.salers.juaga.packets;

import java.lang.reflect.Field;
import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gg.salers.juaga.checks.Check;
import gg.salers.juaga.checks.CheckManager;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.data.JPlayerManager;
import gg.salers.juaga.utils.JPlayerUseAction;
import gg.salers.juaga.utils.Reflection;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction;

public class PacketListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws InstantiationException, IllegalAccessException {
		JPlayer jPlayer = new JPlayer(event.getPlayer().getUniqueId());
		JPlayerManager.getInstance().jPlayers.put(event.getPlayer().getUniqueId(), jPlayer);
		injectPlayer(event.getPlayer());
		for (Class<? extends Check> clazz : CheckManager.checks) {

			Check instance = clazz.newInstance();
			jPlayer.getChecks().add(instance);

		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onLeave(PlayerQuitEvent event) {
		JPlayerManager.getInstance().jPlayers.remove(event.getPlayer().getUniqueId(),
				JPlayerManager.getInstance().jPlayers.get(event.getPlayer().getUniqueId()));
		removePlayer(event.getPlayer());

	}

	private void removePlayer(Player player) {
		io.netty.channel.Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
			return null;
		});
	}

	private void injectPlayer(Player player) {

		JPlayer jPlayer = JPlayerManager.getInstance().getJPlayer(player);
		if (jPlayer == null)
			return;
		ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

			@Override
			public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
				

			 if (packet instanceof PacketPlayInUseEntity) {
					int entityId = (int) Objects.requireNonNull(Reflection.invokeField(packet, "a"));
					Packet<?> packetUse = (PacketPlayInUseEntity) packet;
					for (Check checks : jPlayer.getChecks()) {
						checks.handle(new JPacket(PacketType.USE_ENTITY, packetUse), jPlayer);
					}

					if (((PacketPlayInUseEntity) packet).a() == EnumEntityUseAction.ATTACK) {
						jPlayer.setAction(JPlayerUseAction.ATTACK);

					} else if (((PacketPlayInUseEntity) packet).a() == EnumEntityUseAction.INTERACT) {
						jPlayer.setAction(JPlayerUseAction.INTERACT);
					} else if (((PacketPlayInUseEntity) packet).a() == EnumEntityUseAction.INTERACT_AT) {
						jPlayer.setAction(JPlayerUseAction.INTERACT_AT);
					}

					for (Entity enties : jPlayer.getPlayer().getLocation().getChunk().getEntities()) {
						if (enties.getEntityId() == entityId) {
							jPlayer.setLastTarget((LivingEntity) enties);
						}
					}

				} else if (packet instanceof PacketPlayInFlying) {
					Packet<?> packetFlying = (PacketPlayInFlying) packet;

					for (Check checks : jPlayer.getChecks()) {
						checks.handle(new JPacket(PacketType.FLYING, packetFlying), jPlayer);
					}
					jPlayer.getPastLocations().add(jPlayer.getPlayer().getLocation());
					if (jPlayer.getPastLocations().size() > 4) {
						PacketPlayInFlying packetF = new PacketPlayInFlying();
						jPlayer.setTo(new Location(jPlayer.getPlayer().getWorld(), packetF.a(), packetF.b(), packetF.c()));
						jPlayer.setFrom(jPlayer.getPastLocations().get(1));
					}

				} else if (packet instanceof PacketPlayInArmAnimation) {
					Packet<?> packetAnimation = (PacketPlayInArmAnimation) packet;
					for (Check checks : jPlayer.getChecks()) {
						checks.handle(new JPacket(PacketType.ARM_ANIMATION, packetAnimation), jPlayer);
					}
				} else if (packet instanceof PacketPlayInBlockDig) {
					Packet<?> packetDig = (PacketPlayInBlockDig) packet;
					for (Check checks : jPlayer.getChecks()) {
						checks.handle(new JPacket(PacketType.DIGGING, packetDig), jPlayer);
					}
				}

				super.channelRead(channelHandlerContext, packet);
			}

			@Override
			public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise)
					throws Exception {

				super.write(channelHandlerContext, packet, channelPromise);
			}

			@Override
			public void exceptionCaught(ChannelHandlerContext channel, Throwable th) throws Exception {
				th.printStackTrace();
				super.exceptionCaught(channel, th);
			}
		};

		ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel
				.pipeline();
		pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

	}
}
