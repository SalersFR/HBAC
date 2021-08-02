package gg.salers.honeybadger.check.checks.combat.killaura;

import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.check.CheckData;
import gg.salers.honeybadger.check.Packet;
import gg.salers.honeybadger.data.PlayerData;

@CheckData(name = "KillAura (C)",experimental = true)
public class KillAuraC extends Check {

    @Override
    public void onPacket(Packet packet, PlayerData playerData) {
        //TODO ACCURACY CHECK

    }
}
