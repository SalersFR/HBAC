package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;

@Getter
@Setter
public class CombatProcessor extends Processor {

    private LivingEntity attacked, lastAttacked;
    private EnumWrappers.EntityUseAction action;
    private long lastAttack;

    private boolean attacking;

    public CombatProcessor(PlayerData data) {
        super(data);
    }


    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            action = event.getPacket().getEntityUseActions().read(0);
            setLastAttacked(attacked);
            attacked = (LivingEntity)
                    event.getPacket().getEntityModifier(event.getPlayer().getWorld()).read(0);
            lastAttack = System.currentTimeMillis();
            getData().getNetworkProcessor().addKeepAliveTask(() -> attacking = true);
        } else if (event.getPacketType() == PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION) {
            if (attacking)
                getData().getNetworkProcessor().addKeepAliveTask(() -> attacking = false);
        }
    }

    @Override
    public void processOut(PacketEvent event) {

    }
}
