package gg.salers.honeybadger.check;

import com.comphenix.protocol.events.PacketEvent;
import gg.salers.honeybadger.HoneyBadger;
import gg.salers.honeybadger.data.PlayerData;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.io.IOException;

@Getter
@Setter
public abstract class Check {

    private String name;
    private char type;
    private int vl, probabilty, delay;
    private boolean experimental;


    /**
     * Method for doing checks
     *
     * @param playerData the data reliated to the check
     * @param event      the packet event from ProtocolLib
     **/

    public abstract void onPacket(PacketEvent event, PlayerData playerData) throws IOException;

    /**
     * Get the @CheckData in a check class
     *
     * @return the @CheckData annotation of the check
     */

    public CheckData getCheckData() {
        if (this.getClass().isAnnotationPresent(CheckData.class)) {
            return this.getClass().getAnnotation(CheckData.class);
        } else {
            System.err.println("@CheckData hasn't been added to the class " + this.getClass().getSimpleName() + ".");
        }
        return null;
    }

    /**
     * Method for sending alerts
     *
     * @param data     the potential cheater's data
     * @param moreInfo more info on the check
     */

    protected void flag(PlayerData data, String moreInfo) {

        /** Exempting the player for prevent falses  **/
        if (data.getBukkitPlayerFromUUID().getAllowFlight() ||
                data.getBukkitPlayerFromUUID().getGameMode() == GameMode.SPECTATOR ||
                data.getBukkitPlayerFromUUID().getGameMode() == GameMode.CREATIVE) return;

        this.name = this.getCheckData().name().split("\\(")[0].replace(" ", "");
        this.type = this.getCheckData().name().split("\\(")[1].split("\\)")[0].replaceAll(" ",
                "").toCharArray()[0];
        this.experimental = this.getCheckData().experimental();


        TextComponent toSendExp = new TextComponent(ChatColor.translateAlternateColorCodes('&', HoneyBadger.getInstance().getConfig()
                .getString("honeybadger.alert-message").replaceAll("%player%", data.getBukkitPlayerFromUUID().getName()).
                        replaceAll("%check%", this.name).replaceAll("%type%", String.valueOf(this.type)).
                        replaceAll("%exp%", "&7(Experimental)").replaceAll("%vl%",
                        String.valueOf(vl)).replaceAll("%probabilty%", String.valueOf(probabilty))));

        TextComponent toSend = new TextComponent(ChatColor.translateAlternateColorCodes('&', HoneyBadger.getInstance().getConfig()
                .getString("honeybadger.alert-message").replaceAll("%player%", data.getBukkitPlayerFromUUID().getName()).
                        replaceAll("%check%", this.name).replaceAll("%type%", String.valueOf(this.type)).
                        replaceAll("%exp%", "").replaceAll("%vl%",
                        String.valueOf(vl)).replaceAll("%probabilty%", String.valueOf(probabilty))));


        toSend.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.
                translateAlternateColorCodes('&', "&8[&c&lHBAC&8]\n" + "&cInfo:&7 " + moreInfo
                        + "\n&cExperimental:&7 " + experimental + "\n&cProbability: &7" + probabilty +
                        "\n\n&cClick to teleport")).create()));
        toSendExp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.
                translateAlternateColorCodes('&', "&8[&c&lHBAC&8]\n" + "&cInfo:&7 " + moreInfo
                        + "\n&cExperimental:&7 " + experimental + "\n&cProbability: &7" + probabilty +
                        "\n\n&cClick to teleport")).create()));
        toSend.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "tp " + data.getBukkitPlayerFromUUID().getName()));
        toSendExp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + data.getBukkitPlayerFromUUID().getName()));




        if (this.probabilty > 5) {
            probabilty = 5;
        }
        if (this.probabilty == 0) {
            this.probabilty = 1;
        }
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (onlinePlayers.hasPermission("hbac.alerts")) {
                if (++delay > 5) {
                    vl++;
                    if (this.experimental) {
                        onlinePlayers.spigot().sendMessage(toSendExp);
                    } else onlinePlayers.spigot().sendMessage(toSend);
                    if (this.vl > HoneyBadger.getInstance().getConfig().getInt("honeybadger.punish-vl")) {
                        this.punish(data);

                    }


                    delay = 0;

                }

            }
        }


    }

    /**
     * @param toVerbose the thing to broadcast
     */
    protected void verbose(Object toVerbose) {
        Bukkit.broadcastMessage("§c§lVERBOSE: §f" + toVerbose);

    }

    /**
     * @param data the player to punish
     */
    protected void punish(PlayerData data) {
            String toDispatch = ChatColor.translateAlternateColorCodes('&', HoneyBadger.getInstance().
                    getConfig().getString("honeybadger.punish-command").replaceAll("%player%", data.getBukkitPlayerFromUUID().getName()).
                    replaceAll("%check%", this.name).replaceAll("%type%", String.valueOf(this.type)).
                    replaceAll("%exp%", "").replaceAll("%vl%",
                    String.valueOf(vl)).replaceAll("%probabilty%", String.valueOf(probabilty)));
            Bukkit.getScheduler().runTask(HoneyBadger.getInstance(),() -> Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),toDispatch));
            data.getBukkitPlayerFromUUID().getWorld().strikeLightningEffect(data.getBukkitPlayerFromUUID().getLocation());
            this.vl = this.probabilty = this.delay = 0;



    }
}
