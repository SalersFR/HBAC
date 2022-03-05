package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.check.Check;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DebugCommand implements HCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().getPlayerData((Player)sender);

        if (args.length < 2) {
            if(data.getDebugChecks().isEmpty()) {
                sender.sendMessage(ChatColor.RED + "/debug (check)");
            } else {
                sender.sendMessage("§cDebugging checks §7(§r" + data.getDebugChecks().size() + "§7): " + data.getDebugChecks().stream().map(Check::getName).reduce((a, b) -> a + ", " + b).get());
            }
            return;
        }

        StringBuilder checkName = new StringBuilder();
        int a = args.length - 1;
        for (int i = 1; i < a; i++) {
            checkName.append(args[i]).append(" ");
        }
        checkName.append(args[a]);

        final Optional<Check> checkOptional = data.getChecks().stream().filter(check -> (check.getName() + " " +
                check.getType()).equalsIgnoreCase(checkName.toString())).findAny();

        if(checkOptional.isPresent()) {
            if (!data.getDebugChecks().contains(checkOptional.get())) {
                data.getDebugChecks().add(checkOptional.get());

                sender.sendMessage("§cNow debugging " + checkName);
            } else {
                data.getDebugChecks().remove(checkOptional.get());

                sender.sendMessage("§cStopped debugging " + checkName + ".");
            }
        } else {
            sender.sendMessage("§cCould not find check " + checkName);
        }
    }

    @Override
    public boolean canConsoleUse() {
        return false;
    }

    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public String getDescription() {
        return "provides useful information about a check";
    }


}
