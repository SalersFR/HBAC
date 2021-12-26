package gg.salers.honeybadger.check.checks.movement.speed;

import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.utils.HPacket;
import gg.salers.honeybadger.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckData(name = "Speed (B)", experimental = true)
public class SpeedB extends Check {
    // TODO: exempt on teleport & velocity because those aren't handled

    private float lastFriction = 0, friction = 0;
    private double lastDeltaXZ;
    private boolean wasOnGround;
    private int ground = 0, air = 0;


    @Override
    public void onPacket(HPacket packet, PlayerData playerData) {
        if (packet.isFlying()) {
            // ground values
            boolean onGround = packet.getContainer().getBooleans().read(0);
            boolean wasOnGround = this.wasOnGround;
            this.wasOnGround = onGround;

            air = onGround ? 0 : Math.min(air + 1, 20);
            ground = onGround ? Math.min(ground + 1, 20) : 0;

            // deltas
            double deltaXZ = playerData.getMovementProcessor().getDeltaXZ();
            double lastDeltaXZ = this.lastDeltaXZ;
            this.lastDeltaXZ = deltaXZ;

            // landMovementFactor
            float speed = PlayerUtils.getPotionLevel(playerData.getBukkitPlayerFromUUID(), PotionEffectType.SPEED);
            float slow = PlayerUtils.getPotionLevel(playerData.getBukkitPlayerFromUUID(), PotionEffectType.SLOW);
            double d = 0.10000000149011612;
            d += d * 0.20000000298023224 * speed;
            d += d * -0.15000000596046448 * slow;

            // Sprint desync big gay just assume they are sprinting
            d += d * 0.30000001192092896;

            float landMovementFactor = (float) d;

            // the check itself
            double prediction;
            if (ground > 2) {
                prediction = lastDeltaXZ * 0.91f * getBlockFriction(playerData) + landMovementFactor;
            } else if (air == 1) {
                prediction = lastDeltaXZ * 0.91f + 0.2f + landMovementFactor;
            } else if (ground == 2) {
                prediction = lastDeltaXZ * 0.91f + landMovementFactor;
            } else {
                prediction = lastDeltaXZ * 0.91f + 0.026f;
            }
            if (prediction < playerData.getBukkitPlayerFromUUID().getWalkSpeed() + 0.02 * (speed + 1))
                prediction = playerData.getBukkitPlayerFromUUID().getWalkSpeed() + 0.02 * (speed + 1);

            // very lazy patch for a false flag
            if (ground > 1) {
                this.lastFriction = this.friction;
                this.friction = getBlockFriction(playerData) * 0.91f;
            }

            if (friction < lastFriction)
                prediction += landMovementFactor * 1.25;




            // flag
            if (deltaXZ > prediction && playerData.getMovementProcessor().getTicksSinceHurt() > 32) {

                if (++buffer > 6) {
                    flag(playerData, "p=" + prediction + " d=" + deltaXZ);
                }
            } else if(buffer > 0)buffer -= 0.5D;
        }
    }

    public float getBlockFriction(PlayerData playerData) {
        String block = playerData.getBukkitPlayerFromUUID().getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase();
        return block.equals("blue ice") ? 0.989f : block.contains("ice") ? 0.98f : block.equals("slime") ? 0.8f : 0.6f;
    }
}