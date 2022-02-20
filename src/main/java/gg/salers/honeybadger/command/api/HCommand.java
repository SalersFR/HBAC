package gg.salers.honeybadger.command.api;


import gg.salers.honeybadger.data.PlayerData;

public interface HCommand {

    void handle(final PlayerData sender, final String[] args);

    String getName();
}
