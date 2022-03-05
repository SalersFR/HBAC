package gg.salers.honeybadger.command.impl;

import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;


public class InfoCommand implements HCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage("§cUsage: /hbac info <player>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        if(target != null && target.isOnline()) {
            PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().getPlayerData(target.getPlayer());
            if(data != null) {
                sender.sendMessage("§7" + target.getName() + ":");
                sender.sendMessage("§7- VL: §r" + data.getViolations());
                sender.sendMessage("§7- Client: §r" + data.getClient().getBrand() + "§7(§r" + data.getClient().getVersion() + "§7)");
            } else {
                sender.sendMessage("§cPlayer has no data");
            }
        } else {
            sender.sendMessage("§cPlayer not found");
            return;
        }
    }

    @Override
    public boolean canConsoleUse() {
        return true;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "shows info about a player";
    }
}
