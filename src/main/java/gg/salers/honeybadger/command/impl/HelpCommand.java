package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;

public class HelpCommand implements HCommand {

    @Override
    public void handle(PlayerData sender, String[] args) {
        sender.getPlayer().sendMessage(   "§cHoneyBadger\n§c" +
                "§c\n§c" +
                "§7- §c/hbac help §7shows this.\n" +
                "§7- §c/hbac alerts §7toggles the display of flags.\n" +
                "§7- §c/hbac debug <check> §7sends the debug of the targeted check.\n" +
                "§7- §c/hbac reload §7 reload all config files.\n" +
                " \n" +
                "§cHBAC, made by Salers.\n");
    }

    @Override
    public String getName() {
        return "help";
    }
}
