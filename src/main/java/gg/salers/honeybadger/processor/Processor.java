package gg.salers.honeybadger.processor;

import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.data.PlayerData;

public abstract class Processor {

    private final PlayerData data;

    public  Processor(PlayerData data) {
        this.data = data;
    }

    public abstract void processIn(PacketEvent event);

    public abstract void processOut(PacketEvent event);
}
