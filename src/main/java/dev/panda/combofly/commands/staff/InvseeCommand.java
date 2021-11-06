package dev.panda.combofly.commands.staff;

import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InvseeCommand extends BaseCommand {

    @Command(name = "invsee", permission = "pandafly.invsee", aliases = "inventorysee")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (args.length < 1){
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            player.sendMessage(ChatUtil.translate("&cPlayer not found."));
            return;
        }
        if (player.equals(target)) {
            player.sendMessage(ChatUtil.translate("&cYou cant see yourself inventory."));
            return;
        }
        player.openInventory(target.getInventory());
    }
}
