package gg.salers.honeybadger.processor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import gg.salers.honeybadger.HoneyBadger;
import lombok.Data;
import org.bukkit.entity.LivingEntity;

@Data
public class CombatProcessor {

    private LivingEntity attacked,lastAttacked;
    private EnumWrappers.EntityUseAction action;
    private long lastAttack;

    private boolean attacking;
    public CombatProcessor() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),
                PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                action = event.getPacket().getEntityUseActions().read(0);
                attacked = (LivingEntity)
                        event.getPacket().getEntityModifier(event.getPlayer().getWorld()).read(0);
                setLastAttacked(attacked);
                lastAttack = System.currentTimeMillis();
                attacking = true;
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),
                PacketType.Play.Client.KEEP_ALIVE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                attacking = false;
            }
        });

    }
}
