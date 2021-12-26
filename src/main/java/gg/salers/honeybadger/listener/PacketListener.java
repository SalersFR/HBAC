package gg.salers.honeybadger.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;

public class PacketListener {


    public PacketListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(), PacketType.values()) {


            @Override
            public void onPacketReceiving(PacketEvent event) {

                /** getting the player's data **/
                PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().
                        getPlayerData(event.getPlayer().getUniqueId());

                /** handling data processor using ingoing packets**/
                data.getMovementProcessor().processIn(event);
                data.getCombatProcessor().processIn(event);
                data.getRotationProcessor().processIn(event);
                data.getNetworkProcessor().processIn(event);
                data.getClickProcessor().processIn(event);

                /** handling all PlayerData's Checks **/
                for (Check checks : data.getChecks()) {
                    checks.onPacket(new HPacket(event.getPacketType(),event.getPacket()), data);
                }
            }

            @Override
            public void onPacketSending(PacketEvent event) {

                /** getting the player's data **/
                PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().
                        getPlayerData(event.getPlayer().getUniqueId());


                /** handling data processor using outgoing packets**/
                data.getNetworkProcessor().processOut(event);

                /** handling all PlayerData's Checks **/
                for (Check checks : data.getChecks()) {
                    checks.onPacket(new HPacket(event.getPacketType(),event.getPacket()), data);
                }
            }


        });
    }


}
