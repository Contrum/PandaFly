package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.combofly.utilities.TimeUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class StartCommand extends BaseCommand {

    @Command(name = "koth.start", permission = "pandafly.koth.start")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        String label = commandArgs.getLabel();

        if (args.length == 0 || args.length == 1) {
            player.sendMessage(ChatUtil.translate("&cUsage: /koth start (name) (time)"));
            return;
        }

        String name = args[0];
        long time = TimeUtil.parseTime(args[1]);
        KoTH koth = KoTH.getFromName(name);
        if (koth != null) {
            koth.start(time);
            player.sendMessage(ChatUtil.translate("&aThe KoTH &b" + name + " &ahas been Started!"));
        } else {
            player.sendMessage(ChatUtil.translate("&cPlease usage a valid KoTH name."));
        }
    }
}
