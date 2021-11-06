package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.Utils;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PingCommand extends BaseCommand {

    @Command(name = "ping", permission = "pandafly.ping")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("PING.PLAYER")
                    .replace("{player}", player.getName())
                    .replace("{ping}", String.valueOf(Utils.getPlayerPing(player)))));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatUtil.translate("&cPlayer not found."));
            return;
        }

        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("PING.TARGET")
                .replace("{target}", target.getName())
                .replace("{ping}", String.valueOf(Utils.getPlayerPing(target)))));
    }
}
