package gg.salers.honeybadger.processor;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import lombok.Data;

@Data
public class NetworkProcessor {

    private int kpPing;
    private long lastServerKeepAlive,lastFlying;
    public NetworkProcessor() {

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),
                PacketType.Play.Client.KEEP_ALIVE,PacketType.Play.Server.KEEP_ALIVE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                kpPing = (int) Math.abs(System.currentTimeMillis() - lastServerKeepAlive);
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                lastServerKeepAlive = System.currentTimeMillis();
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),
                PacketType.Play.Client.FLYING) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                lastFlying = System.currentTimeMillis();

            }
        });
    }
}
