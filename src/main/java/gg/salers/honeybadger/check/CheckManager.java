package gg.salers.honeybadger.check;

import gg.salers.honeybadger.check.checks.combat.aim.AimA;
import gg.salers.honeybadger.check.checks.combat.aim.AimB;
import gg.salers.honeybadger.check.checks.combat.aim.AimC;
import gg.salers.honeybadger.check.checks.combat.autoclicker.AutoclickerA;
import gg.salers.honeybadger.check.checks.combat.killaura.*;
import gg.salers.honeybadger.check.checks.combat.reach.ReachA;
import gg.salers.honeybadger.check.checks.misc.ground.GroundA;
import gg.salers.honeybadger.check.checks.misc.protocol.ProtocolA;
import gg.salers.honeybadger.check.checks.misc.protocol.ProtocolB;
import gg.salers.honeybadger.check.checks.movement.flight.FlightA;
import gg.salers.honeybadger.check.checks.movement.flight.FlightB;
import gg.salers.honeybadger.check.checks.movement.flight.FlightC;
import gg.salers.honeybadger.check.checks.movement.speed.SpeedA;
import gg.salers.honeybadger.check.checks.movement.step.StepA;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class CheckManager {

    private final PlayerData data;

    private final List<Check> checks;

    public CheckManager(PlayerData data) {
        this.data = data;
        this.checks = Arrays.asList(

                new AimA(),
                new AimB(),
                new AimC(),
                new AutoclickerA(),
                new KillAuraA(),
                new KillAuraB(),
                new KillAuraC(),
                new KillAuraD(),
                new KillAuraE(),
                new ReachA(),

                new GroundA(),
                new ProtocolA(),
                new ProtocolB(),

                new FlightA(),
                new FlightB(),
                new FlightC(),
                new SpeedA(),
                new StepA()
                //new JumpA()


        );
    }
}
