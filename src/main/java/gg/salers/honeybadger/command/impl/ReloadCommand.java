package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements HCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        HoneyBadger.getInstance().reloadConfig();

        sender.sendMessage(
                "§a"+ "Successfully reloaded config."
        );
    }

    @Override
    public boolean canConsoleUse() {
        return true;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reloads the config";
    }
}
