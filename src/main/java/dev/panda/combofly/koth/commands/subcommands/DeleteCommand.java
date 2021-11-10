package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class DeleteCommand extends BaseCommand {

    @Command(name = "koth.delete", permission = "pandafly.koth.delete")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(ChatUtil.translate("&cUsage: /koth delete (name)"));
            return;
        }

        String name = args[0];
        KoTH koth = KoTH.getFromName(name);
        if (koth != null) {
            koth.delete();
            player.sendMessage(ChatUtil.translate("&aKoTH &b" + name + " &ahas been deleted!"));
        } else {
            player.sendMessage(ChatUtil.translate("&cPlease insert a valid KoTH name!"));
        }
    }
}
