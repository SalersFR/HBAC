package gg.salers.honeybadger.command.api;


import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.command.CommandSender;

public interface HCommand {

    void handle(final CommandSender sender, final String[] args);

    boolean canConsoleUse();

    String getName();
    String getDescription();
}
