package gg.salers.honeybadger.processor.impl;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkProcessor extends Processor {

    private int kpPing;
    private long lastServerKeepAlive,lastFlying;
    private PlayerData data;
    public NetworkProcessor(PlayerData data) {
        super(data);


    }

    @Override
    public void processIn(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.KEEP_ALIVE) {
            kpPing = (int) (System.currentTimeMillis() - lastServerKeepAlive);

        }else if(event.getPacketType() == PacketType.Play.Client.FLYING) {
            lastFlying = System.currentTimeMillis();
        }
    }

    @Override
    public void processOut(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.KEEP_ALIVE) {
            lastServerKeepAlive = System.currentTimeMillis();
        }

    }




}
