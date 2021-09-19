package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;

@Getter
@Setter
public class CombatProcessor extends Processor {

    private LivingEntity attacked,lastAttacked;
    private EnumWrappers.EntityUseAction action;
    private long lastAttack;



    private boolean attacking;

    public CombatProcessor(PlayerData data) {
        super(data);
    }



    @Override
    public void processIn(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
            action = event.getPacket().getEntityUseActions().read(0);
            attacked = (LivingEntity)
                    event.getPacket().getEntityModifier(event.getPlayer().getWorld()).read(0);
            setLastAttacked(attacked);
            lastAttack = System.currentTimeMillis();
            attacking = true;
        }else if(event.getPacketType() == PacketType.Play.Client.KEEP_ALIVE) {
            attacking = false;
        }
    }

    @Override
    public void processOut(PacketEvent event) {

    }
}
