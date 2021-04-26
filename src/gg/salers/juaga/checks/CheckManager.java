package gg.salers.juaga.checks;

import java.util.Arrays;
import java.util.List;

import gg.salers.juaga.checks.impl.combat.aura.AuraPacketDelayCheck;
import gg.salers.juaga.checks.impl.combat.autoclicker.AutoClickerConsistentDelayCheck;
import gg.salers.juaga.checks.impl.combat.autoclicker.AutoClickerConsistentSpeedCheck;
import gg.salers.juaga.checks.impl.combat.autoclicker.AutoClickerFastClickCheck;
import gg.salers.juaga.checks.impl.combat.reach.ReachBoundingBoxCheck;
import gg.salers.juaga.checks.impl.combat.reach.ReachDistanceCheck;
import gg.salers.juaga.checks.impl.movement.fly.FlyAirJumpCheck;
import gg.salers.juaga.checks.impl.movement.fly.FlyPredictionCheck;
import gg.salers.juaga.checks.impl.movement.jump.HighJumpLimitCheck;
import gg.salers.juaga.checks.impl.movement.speed.SpeedPredictionCheck;
import gg.salers.juaga.checks.player.nofall.NoFallSpoofGroundCheck;
import gg.salers.juaga.checks.player.protocol.ProtocolInvalidPitchCheck;
import gg.salers.juaga.checks.player.protocol.ProtocolSelfHitCheck;

public class CheckManager {

	public final static List<Class<? extends Check>> checks = Arrays.asList(AuraPacketDelayCheck.class, FlyAirJumpCheck.class,
			FlyPredictionCheck.class, AutoClickerConsistentDelayCheck.class, AutoClickerFastClickCheck.class,
			AutoClickerConsistentSpeedCheck.class, ReachBoundingBoxCheck.class, ReachDistanceCheck.class,
			ProtocolInvalidPitchCheck.class, ProtocolSelfHitCheck.class, NoFallSpoofGroundCheck.class,
			HighJumpLimitCheck.class, SpeedPredictionCheck.class);

}
