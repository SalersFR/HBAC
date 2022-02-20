package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;

public class AlertsCommand implements HCommand {

    @Override
    public void handle(PlayerData sender, String[] args) {

        final boolean isAlerting = HoneyBadger.getInstance().getAlerting().contains(sender.getPlayer());
        final String toSend = isAlerting ? "§cYou've disabled flags display." : "§aYou've enabled flags display";

        sender.getPlayer().sendMessage(toSend);

        if (isAlerting) {
            HoneyBadger.getInstance().getAlerting().remove(sender.getPlayer());
        } else {
            HoneyBadger.getInstance().getAlerting().add(sender.getPlayer());
        }

    }

    @Override
    public String getName() {
        return "alerts";
    }
}
