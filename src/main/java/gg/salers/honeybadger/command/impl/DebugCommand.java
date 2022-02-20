package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.ChatColor;

import java.util.Optional;

public class DebugCommand implements HCommand {

    @Override
    public void handle(PlayerData sender, String[] args) {
        if (args.length < 2) {
            sender.getPlayer().sendMessage(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            "&cUsage: /debug <check>."
                    )
            );
            return;
        }

        final String checkName = args[1];

        final Optional<Check> checkOptional = sender.getChecks().stream().filter(check -> (check.getType() +
                check.getName()).equalsIgnoreCase(checkName)).findAny();

        if (checkOptional.isPresent() && sender.getDebugging() == null) {
            sender.setDebugging(checkOptional.get());

            sender.getPlayer().sendMessage("§cNow debugging " + checkName);
        } else {
            sender.setDebugging(null);

            sender.getPlayer().sendMessage("§cDisabled your debug messages.");
        }
    }

    @Override
    public String getName() {
        return "debug";
    }


}
