package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.Selection;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.entity.Player;

public class WandCommand extends BaseCommand {

    @Command(name = "koth.wand", permission = "pandafly.koth.wand")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        player.sendMessage(CC.translate("&eYou have received the KoTH Wand"));
        player.getInventory().addItem(Selection.SELECTION_WAND);
    }
}
