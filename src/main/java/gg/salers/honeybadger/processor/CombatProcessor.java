package gg.salers.honeybadger.processor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Data;
import org.bukkit.entity.LivingEntity;

@Data
public class CombatProcessor {

    private LivingEntity attacked,lastAttacked;
    private EnumWrappers.EntityUseAction action;
    private long lastAttack;

    private PlayerData data;

    private boolean attacking;

    public CombatProcessor(PlayerData data) {
        this.data = data;
    }

    public void handleCombat(PacketEvent event) {
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
}
