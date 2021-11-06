package dev.panda.combofly.commands.staff;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportAllCommand extends BaseCommand {

    @Command(name = "teleportall", permission = "pandafly.teleportall", aliases = "tpall")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        Location location = player.getLocation();
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            players.teleport(location);
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("TPALL.PLAYER")));
        }
    }
}