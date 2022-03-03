package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import gg.salers.honeybadger.utils.Cuboid;
import gg.salers.honeybadger.utils.LocationUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Salers
 * Made the 20/02/2022
 */

@Getter
public class CollisionProcessor extends Processor {

    private int clientAirTicks, mathAirTicks, collisionAirTicks, clientGroundTicks, mathGroundTicks, collisionGroundTicks;
    private boolean nearBoat, inLiquid, inWeb, onClimbable, mathOnGround, onSlime, lastGroundSlime,
            clientOnGround, collisionOnGround, blockNearHead;
    private Cuboid cuboidColl;
    private List<Block> blocks = new ArrayList<>();

    public CollisionProcessor(PlayerData data) {
        super(data);
    }

    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION) {

            final StructureModifier<Double> doubles = event.getPacket().getDoubles();

            cuboidColl = new Cuboid(doubles.read(0), doubles.read(1) - 0.03125, doubles.read(2));

            Bukkit.getScheduler().runTask(HoneyBadger.getInstance(), () -> blocks = (cuboidColl.getBlocks(getData().getPlayer().getWorld())));

            if(blocks == null) return;

            final Location location = new Location(getData().getPlayer().getWorld(),
                    doubles.read(0), doubles.read(1), doubles.read(2));


            nearBoat = LocationUtils.isNearBoat(getData().getPlayer());
            inLiquid = blocks.stream().anyMatch(Block::isLiquid) | LocationUtils.isInLiquid(getData().getPlayer());
            inWeb = blocks.stream().anyMatch(block -> block.getType() == Material.WEB);
            onClimbable = blocks.stream().anyMatch(block -> block.getType() == Material.LADDER ||
                    block.getType() == Material.VINE) || LocationUtils.isCollidingWithWeb(getData().getPlayer());
            onSlime = blocks.stream().anyMatch(block -> block.getType() == Material.SLIME_BLOCK);

            clientOnGround = event.getPacket().getBooleans().read(0);
            mathOnGround = doubles.read(1) % 0.015625 <= 0.001;
            collisionOnGround = blocks.stream().anyMatch(block -> !block.isEmpty());

            blockNearHead = LocationUtils.haveABlockNearHead(location) || LocationUtils.blockNearHead(location);

            lastGroundSlime = (clientOnGround || collisionOnGround || mathOnGround) && onSlime;


            if (collisionOnGround) {
                collisionAirTicks = 0;
                collisionGroundTicks++;
            } else {
                collisionAirTicks++;
                collisionGroundTicks = 0;
            }

            if (clientOnGround) {
                clientAirTicks = 0;
                clientGroundTicks++;
            } else {
                clientAirTicks++;
                clientGroundTicks = 0;
            }

            if (mathOnGround) {
                mathAirTicks = 0;
                mathGroundTicks++;
            } else {
                mathAirTicks++;
                mathGroundTicks = 0;
            }


        }

    }

    @Override
    public void processOut(PacketEvent event) {

    }
}
