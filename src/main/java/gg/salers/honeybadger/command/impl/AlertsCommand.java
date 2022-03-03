package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand implements HCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {

        PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().getPlayerData((Player) sender);

        final boolean isAlerting = HoneyBadger.getInstance().getAlerting().contains(data.getPlayer());
        final String toSend = isAlerting ? "§cYou've disabled alerts." : "§aYou've enabled alerts";

        if (isAlerting) {
            HoneyBadger.getInstance().getAlerting().remove(data.getPlayer());
        } else {
            HoneyBadger.getInstance().getAlerting().add(data.getPlayer());
        }

        sender.sendMessage(toSend);
    }

    @Override
    public boolean canConsoleUse() {
        return false;
    }

    @Override
    public String getName() {
        return "alerts";
    }

    @Override
    public String getDescription() {
        return "toggles alerts";
    }
}
