package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.Selection;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class WandCommand extends BaseCommand {

    @Command(name = "koth.wand", permission = "pandafly.koth.wand")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        player.sendMessage(ChatUtil.translate("&eYou have received the KoTH Wand"));
        player.getInventory().addItem(Selection.SELECTION_WAND);
    }
}
