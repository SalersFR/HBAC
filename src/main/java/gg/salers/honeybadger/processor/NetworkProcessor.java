package gg.salers.honeybadger.processor;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class NetworkProcessor {

    private int kpPing;
    private long lastServerKeepAlive,lastFlying;

    private PlayerData data;

    public NetworkProcessor(PlayerData data) {
        this.data = data;

    }

    public void handleReceiving(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.KEEP_ALIVE) {
            kpPing = (int) Math.abs(System.currentTimeMillis() - lastServerKeepAlive);
        }else if(event.getPacketType() == PacketType.Play.Client.FLYING) {
            lastFlying = System.currentTimeMillis();
        }
    }

    public void handleSend(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.KEEP_ALIVE) {
            lastServerKeepAlive = System.currentTimeMillis();
        }
    }
}
