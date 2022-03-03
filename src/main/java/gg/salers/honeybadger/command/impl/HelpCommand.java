package gg.salers.honeybadger.command.impl;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.data.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class HelpCommand implements HCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        StringBuilder commands = new StringBuilder();

        for (HCommand command : HoneyBadger.getInstance().getCommandHandler().getCommands()) {
            if(sender instanceof ConsoleCommandSender) {
                if(command.canConsoleUse()) {
                    commands.append("- /hbac ").append(command.getName()).append(": ").append(command.getDescription()).append("\n");
                }
            } else {
                commands.append("- /hbac ").append(command.getName()).append(": ").append(command.getDescription()).append("\n");
            }
        }

        sender.sendMessage(   "§cHoneyBadger\n§c" + commands.toString() + "§cHBAC, made by Salers.\n");
    }

    @Override
    public boolean canConsoleUse() {
        return true;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "shows this";
    }
}
