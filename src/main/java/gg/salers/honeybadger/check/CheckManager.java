package gg.salers.honeybadger.check;

import gg.salers.honeybadger.check.checks.combat.aim.AimA;
import gg.salers.honeybadger.check.checks.combat.autoclicker.AutoclickerA;
import gg.salers.honeybadger.check.checks.combat.killaura.KillAuraA;
import gg.salers.honeybadger.check.checks.combat.killaura.KillAuraB;
import gg.salers.honeybadger.check.checks.combat.reach.ReachA;
import gg.salers.honeybadger.check.checks.misc.protocol.ProtocolA;
import gg.salers.honeybadger.check.checks.misc.protocol.ProtocolB;
import gg.salers.honeybadger.check.checks.misc.protocol.ground.GroundA;
import gg.salers.honeybadger.check.checks.movement.flight.FlightA;
import gg.salers.honeybadger.check.checks.movement.flight.FlightB;
import gg.salers.honeybadger.check.checks.movement.jump.JumpA;
import gg.salers.honeybadger.check.checks.movement.speed.SpeedA;
import gg.salers.honeybadger.check.checks.movement.speed.SpeedB;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class CheckManager {

    @Getter
    private static final CheckManager instance = new CheckManager();

    /** all the checks classes **/
    public final List<Class<? extends Check>> checksClasses = Arrays.asList(FlightA.class, FlightB.class,
            SpeedA.class, SpeedB.class, KillAuraA.class, GroundA.class
            , KillAuraB.class , ReachA.class, AutoclickerA.class, AimA.class, JumpA.class, ProtocolA.class, ProtocolB.class);

    /**
     * Apply checks to PlayerData
     *
     * @param data the data for who checks will be applied
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void initChecks(PlayerData data) throws IllegalAccessException, InstantiationException {
        for(Class<? extends Check> clazz : checksClasses) {
            Check check = clazz.newInstance();
            data.getChecks().add(check);
        }

    }


}
