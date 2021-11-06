package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class StopCommand extends BaseCommand {

    @Command(name = "koth.stop", permission = "pandafly.koth.stop")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(ChatUtil.translate("&cUsage: /koth stop (name)"));
            return;
        }

        KoTH koth = KoTH.getFromName(args[0]);

        if (koth != null) {
            koth.stop(false);
            player.sendMessage(ChatUtil.translate("&aKoTH stopped correctly"));
        } else {
            player.sendMessage("&cPlease insert a valid koth name!");
        }
    }
}
