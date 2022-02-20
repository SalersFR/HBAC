package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;

public class ReloadCommand implements HCommand {

    @Override
    public void handle(PlayerData sender, String[] args) {
        HoneyBadger.getInstance().reloadConfig();

        sender.getPlayer().sendMessage(
                "§a"+ "Successfully reloaded config."
        );
    }

    @Override
    public String getName() {
        return "reload";
    }
}
