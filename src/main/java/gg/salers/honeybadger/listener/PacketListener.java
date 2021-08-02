package gg.salers.honeybadger.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;
import lombok.SneakyThrows;

public class PacketListener {


    public PacketListener() {
        for (PacketType toListen : PacketType.values()) {
            if(toListen.isSupported()) {


            ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(), toListen) {



                @SneakyThrows
                @Override
                public void onPacketReceiving(PacketEvent event) {

                    /** getting the player's data **/
                    PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().
                            getPlayerData(event.getPlayer().getUniqueId());

                    /** handling data processors **/

                    data.getCombatProcessor().handleReceive(event);
                    data.getMovementProcessor().handle(event);
                    data.getRotationProcessor().handle(event);
                    data.getNetworkProcessor().handleReceiving(event);

                    /** handling all PlayerData's Checks **/
                    for (Check checks : data.getChecks()) {
                        checks.onPacket(new Packet(event.getPacket()), data);
                    }
                }

                @SneakyThrows
                @Override
                public void onPacketSending(PacketEvent event) {


                    /** getting the player's data **/
                    PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().
                            getPlayerData(event.getPlayer().getUniqueId());

                    /** handling data processors **/

                    data.getNetworkProcessor().handleSend(event);
                    data.getCombatProcessor().handleSend(event);

                    /** handling all PlayerData's Checks **/
                    for (Check checks : data.getChecks()) {
                        checks.onPacket(new Packet(event.getPacket()), data);
                    }
                }


            });
        }
    }}

}
