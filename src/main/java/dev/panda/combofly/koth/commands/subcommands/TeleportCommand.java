package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class TeleportCommand extends BaseCommand {

    @Command(name = "koth.teleport", permission = "pandafly.koth.teleport")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(ChatUtil.translate("&cUsage: /koth teleport (name)"));
            return;
        }

        KoTH koth = KoTH.getFromName(args[0]);

        if (koth != null) {
            player.teleport(koth.getCuboid().getCenter());
            player.sendMessage(ChatUtil.translate("&aYou have been teleported to the KoTH successfully"));
        } else {
            player.sendMessage("&cPlease insert a valid koth name!");
        }
    }
}
