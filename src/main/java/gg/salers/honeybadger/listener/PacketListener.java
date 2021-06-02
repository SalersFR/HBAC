package gg.salers.honeybadger.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.data.PlayerData;

public class PacketListener {


    private final PacketType[] toListen = new PacketType[] { PacketType.Play.Client.POSITION,PacketType.Play.Client.FLYING,
            PacketType.Play.Client.POSITION_LOOK,PacketType.Play.Client.LOOK,PacketType.Play.Client.USE_ENTITY,
            PacketType.Play.Client.KEEP_ALIVE,PacketType.Play.Server.KEEP_ALIVE,PacketType.Play.Server.REL_ENTITY_MOVE,
            PacketType.Play.Server.ENTITY_VELOCITY,PacketType.Play.Client.ARM_ANIMATION,PacketType.Play.Client.BLOCK_DIG};
    public PacketListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(HoneyBadger.getInstance(),toListen) {


            @Override
            public void onPacketReceiving(PacketEvent event) {

                /** getting the player's data **/
                PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().
                        getPlayerData(event.getPlayer().getUniqueId());

                /** handling all PlayerData's Checks **/
                for (Check checks : data.getChecks()) {
                    checks.onPacket(event, data);
                }
            }    @Override
            public void onPacketSending(PacketEvent event) {

                /** getting the player's data **/
                PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().
                        getPlayerData(event.getPlayer().getUniqueId());

                /** handling all PlayerData's Checks **/
                for (Check checks : data.getChecks()) {
                    checks.onPacket(event, data);
                }
            }


        });
    }


}
