package dev.panda.combofly.koth.commands.subcommands;

import dev.panda.combofly.koth.Selection;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
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
