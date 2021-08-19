package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class DeleteCommand extends BaseCommand {

    @Command(name = "koth.delete", permission = "pandafly.koth.delete")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /koth delete (name)"));
            return;
        }

        String name = args[0];
        KoTH koth = KoTH.getFromName(name);
        if (koth != null) {
            koth.delete();
            player.sendMessage(CC.translate("&aKoTH &b" + name + " &ahas been deleted!"));
        } else {
            player.sendMessage(CC.translate("&cPlease insert a valid KoTH name!"));
        }
    }
}
