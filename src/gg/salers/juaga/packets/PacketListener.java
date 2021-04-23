package gg.salers.juaga.packets;s

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import gg.salers.juaga.Juaga;
import gg.salers.juaga.checks.Check;
import gg.salers.juaga.data.JPlayer;
import gg.salers.juaga.data.JPlayerManager;
import gg.salers.juaga.utils.JPlayerUseAction;
import gg.salers.juaga.utils.Reflection;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity.EnumEntityUseAction;



public class PacketListener implements Listener{
	@EventHandler
    public void onJoin(PlayerJoinEvent event){
        injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
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
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @SuppressWarnings("deprecation")
			@Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
            	if(packet instanceof PacketPlayInPosition) {
            		Packet<?> packetPosition = (PacketPlayInPosition) packet;
            		Check.getInstance().handle(new JPacket(PacketType.POSITION, packetPosition), jPlayer);
            		jPlayer.setTo(player.getLocation());
            	    Bukkit.getScheduler().scheduleAsyncRepeatingTask(Juaga.getInstance(),() -> {
            	    	jPlayer.setFrom(player.getLocation());
            	    	
            	    },1L,0L);
            	}else if(packet instanceof PacketPlayInUseEntity) {
            		int entityId = (int) Objects.requireNonNull(Reflection.invokeField(packet, "a"));
            		Packet<?> packetUse = (PacketPlayInUseEntity) packet;
            		Check.getInstance().handle(new JPacket(PacketType.USE_ENTITY, packetUse), jPlayer);
            		
            	
            		if(((PacketPlayInUseEntity) packet).a() == EnumEntityUseAction.ATTACK) {
            			jPlayer.setAction(JPlayerUseAction.ATTACK);
            			
            		}else if(((PacketPlayInUseEntity) packet).a() == EnumEntityUseAction.INTERACT) {
            			jPlayer.setAction(JPlayerUseAction.INTERACT);
            		}else if(((PacketPlayInUseEntity) packet).a() == EnumEntityUseAction.INTERACT_AT) {
            			jPlayer.setAction(JPlayerUseAction.INTERACT_AT);
            		}
            		for(Entity enties : jPlayer.getPlayer().getLocation().getChunk().getEntities()) {
            			if(enties.getEntityId() == entityId) {
            				jPlayer.setLastTarget((LivingEntity) enties);
            			}
            		}
            		
            		
     
            	}else if(packet instanceof PacketPlayInFlying) {
            		Packet<?> packetFlying = (PacketPlayInFlying) packet;
            		Check.getInstance().handle(new JPacket(PacketType.FLYING, packetFlying), jPlayer);
            	}
                
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
           
              
                super.write(channelHandlerContext, packet, channelPromise);
            }


        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }
}
