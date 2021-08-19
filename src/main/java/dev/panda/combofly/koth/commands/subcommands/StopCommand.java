package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.KoTH;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class StopCommand extends BaseCommand {

    @Command(name = "koth.stop", permission = "pandafly.koth.stop")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /koth stop (name)"));
            return;
        }

        KoTH koth = KoTH.getFromName(args[0]);

        if (koth != null) {
            koth.stop(false);
            player.sendMessage(CC.translate("&aKoTH stopped correctly"));
        } else {
            player.sendMessage("&cPlease insert a valid koth name!");
        }
    }
}
