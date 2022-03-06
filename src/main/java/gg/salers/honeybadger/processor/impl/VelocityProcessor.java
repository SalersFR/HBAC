package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Salers
 * Made the 20/02/2022
 */

@Getter
public class VelocityProcessor extends Processor {
    private double velX, velY, velZ;
    private int velTicks;

    private Velocity acceptedVelocity = null;

    private HashMap<Integer, Velocity> pending = new HashMap<>();

    public VelocityProcessor(PlayerData data) {
        super(data);
    }

    @Override
    public void processIn(PacketEvent event) {

        if(event.getPacketType() == PacketType.Play.Client.TRANSACTION) {
                final int id = event.getPacket().getIntegers().read(0);
                if(pending.containsKey(id)) {
                    acceptedVelocity = pending.get(id);
                    pending.remove(id);
                    velTicks = 0;
                }
        }

        if (event.getPacketType() == PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION) {

            if (velTicks > 0) {
                if (velY > 0) {
                    velY -= 0.08;
                    velY *= 0.98F;
                } else velY = 0;

                if (velY < 0.005)
                    velY = 0;

            }

            velTicks++;
        }


    }

    @Override
    public void processOut(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.ENTITY_VELOCITY) {
            final StructureModifier<Integer> ints = event.getPacket().getIntegers();

            if (ints.read(0) == getData().getPlayer().getEntityId()) {
                    pending.put(getData().getNetworkProcessor().addTransaction(() -> {
                        velX = ints.read(0) / 8000.0D;
                        velY = ints.read(1) / 8000.0D;
                        velZ = ints.read(2) / 8000.0D;

                        velTicks = 0;


                    }), new Velocity(velX, velY, velZ));

            }


        }

    }

    @Getter
    @AllArgsConstructor
    public static class Velocity {
        private double x, y, z;
    }
}
