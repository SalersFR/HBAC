package gg.salers.honeybadger.command;


import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.command.api.HCommand;
import gg.salers.honeybadger.command.impl.AlertsCommand;
import gg.salers.honeybadger.command.impl.DebugCommand;
import gg.salers.honeybadger.command.impl.HelpCommand;
import gg.salers.honeybadger.command.impl.ReloadCommand;
import gg.salers.honeybadger.data.PlayerData;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
public class CommandHandler implements CommandExecutor {

    private final List<HCommand> commands;

    public CommandHandler() {
        this.commands = Arrays.asList(
                new AlertsCommand(),
                new HelpCommand(),
                new DebugCommand(),
                new ReloadCommand()
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            log.log(Level.ERROR, "Sorry, only players can use commands.");
            return false;
        }

        PlayerData data = HoneyBadger.getInstance().getPlayerDataManager().getPlayerData((Player) sender);

        if (args.length == 0) {
            data.getPlayer().sendMessage(
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
                data.getPlayer().sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                "&cInvalid command argument."
                        )
                );
                return false;
            }

            subCmd.ifPresent(oreoCommand -> oreoCommand.handle(data, args));
        }

        return true;
    }
}
