package dev.panda.lib.chat;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ChatUtil {

    public String LONG_LINE = "&7&m----------------------------------------";
    public String NORMAL_LINE = "&7&m-----------------------------";
    public String SHORT_LINE = "&7&m---------------";

    public String BLUE = ChatColor.BLUE.toString();
    public String AQUA = ChatColor.AQUA.toString();
    public String YELLOW = ChatColor.YELLOW.toString();
    public String RED = ChatColor.RED.toString();
    public String GRAY = ChatColor.GRAY.toString();
    public String GOLD = ChatColor.GOLD.toString();
    public String GREEN = ChatColor.GREEN.toString();
    public String WHITE = ChatColor.WHITE.toString();
    public String BLACK = ChatColor.BLACK.toString();
    public String BOLD = ChatColor.BOLD.toString();
    public String ITALIC = ChatColor.ITALIC.toString();
    public String UNDER_LINE = ChatColor.UNDERLINE.toString();
    public String STRIKE_THROUGH = ChatColor.STRIKETHROUGH.toString();
    public String RESET = ChatColor.RESET.toString();
    public String MAGIC = ChatColor.MAGIC.toString();
    public String DARK_BLUE = ChatColor.DARK_BLUE.toString();
    public String DARK_AQUA = ChatColor.DARK_AQUA.toString();
    public String DARK_GRAY = ChatColor.DARK_GRAY.toString();
    public String DARK_GREEN = ChatColor.DARK_GREEN.toString();
    public String DARK_PURPLE = ChatColor.DARK_PURPLE.toString();
    public String DARK_RED = ChatColor.DARK_RED.toString();
    public String PINK = ChatColor.LIGHT_PURPLE.toString();

    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> translate(List<String> list) {
        return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public String strip(String text) {
        return ChatColor.stripColor(text);
    }

    public String placeholder(Player player, String text, boolean isPlaceholderAPI, boolean colorized) {
        if (colorized) {
            return translate(isPlaceholderAPI ? PlaceholderAPI.setPlaceholders(player, text) : text);
        }
        else {
            return isPlaceholderAPI ? PlaceholderAPI.setPlaceholders(player, text) : text;
        }
    }

    public void sender(CommandSender sender, String text) {
        sender.sendMessage(translate(text));
    }

    public void message(Player player, String text) {
        player.sendMessage(translate(text));
    }

    public void broadcast(String text) {
        Bukkit.broadcastMessage(translate(text));
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public String capitalize(String str) {
        return WordUtils.capitalize(str);
    }

    public String toReadable(Enum<?> enu) {
        return WordUtils.capitalize(enu.name().replace("_", " ").toLowerCase());
    }
}
