package gg.salers.honeybadger.processor.impl;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class NetworkProcessor extends Processor {

    private int kpPing;
    private long lastServerKeepAlive, lastFlying, kpId;
    private PlayerData data;
    private Map<Long, List<Runnable>> keepAliveTasks = new HashMap<>();

    public NetworkProcessor(PlayerData data) {
        super(data);

    }

    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.KEEP_ALIVE) {
            kpPing = (int) (System.currentTimeMillis() - lastServerKeepAlive);
            final long id = event.getPacket().getIntegers().read(0);

            if (keepAliveTasks.containsKey(id)) {
                keepAliveTasks.get(id).forEach(Runnable::run);
                keepAliveTasks.remove(id);
            }

        } else if (event.getPacketType() == PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION) {
            lastFlying = System.currentTimeMillis();
        }
    }

    @Override
    public void processOut(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.KEEP_ALIVE) {
            lastServerKeepAlive = System.currentTimeMillis();
        }

    }

    /**
     * this add a pending task that will be executed when the player will receive the keep alive packet.
     *
     * @param runnable the task that will be executed
     */
    public void addKeepAliveTask(final Runnable runnable) {
        if (kpId > -500)
            kpId = -1000;

        kpId++;

        if (!this.keepAliveTasks.containsKey(kpId)) {
            this.keepAliveTasks.put(kpId, new ArrayList<>());
        }

        this.keepAliveTasks.get(kpId).add(runnable);

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.KEEP_ALIVE);
        packet.getIntegers().write(0, (int) kpId);


        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(data.getPlayer(), packet);
        } catch (InvocationTargetException exception) {
            exception.printStackTrace();
        } catch (NullPointerException e) {
            //slient
        }

    }


}
