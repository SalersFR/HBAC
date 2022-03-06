package gg.salers.honeybadger.command;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.command.impl.*;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
public class CommandHandler implements CommandExecutor {

    @Getter
    private final List<HCommand> commands;

    public CommandHandler() {
        this.commands = Arrays.asList(
                new AlertsCommand(),
                new HelpCommand(),
                new DebugCommand(),
                new ReloadCommand(),
                new InfoCommand()
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes(
                    '&',
                            "&cInvalid argument 'none', try /hbac help."
                    )
            );

            return false;
        }

        if(args[0] != null) {
            String firstArg = args[0];

            final Optional<HCommand> subCmd = commands.stream().filter(command ->
                    command.getName().equalsIgnoreCase(firstArg)).findAny();

            if(!subCmd.isPresent()) {
                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                "&cInvalid command argument."
                        )
                );
                return true;
            }

            if(sender instanceof Player) {
                subCmd.get().handle(sender, args);
            } else if(sender instanceof ConsoleCommandSender) {
                if(subCmd.get().canConsoleUse()) {
                    subCmd.get().handle(sender, args);
                } else {
                    sender.sendMessage("You must be a player to use this command.");
                }
            }
        }

        return true;
    }
}
