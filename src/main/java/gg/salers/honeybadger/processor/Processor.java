package gg.salers.honeybadger.processor;

import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Getter;

public abstract class Processor {

    @Getter
    private final PlayerData data;

    public Processor(PlayerData data) {
        this.data = data;
        data.getProcessors().add(this);
    }

    public abstract void processIn(PacketEvent event);

    public abstract void processOut(PacketEvent event);
}
