package dev.panda.combofly.profile.commands;

import dev.panda.chat.ChatUtil;
import dev.panda.combofly.ComboFly;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EnderChestCommand extends BaseCommand {

    @Command(name = "enderchest", permission = "pandafly.enderchest", aliases = {"ec", "echest"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        
        if (args.length < 1) {
            if (ComboFly.get().getCombatManager().hasCooldown(player)) {
                player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPAWN.COMBAT")));
                return;
            }

            player.openInventory(player.getEnderChest());
            return;
        }

        if (ComboFly.get().getCombatManager().hasCooldown(player)) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("SPAWN.COMBAT")));
            return;
        }

        if (!player.hasPermission("pandafly.enderchest.others")) {
            player.openInventory(player.getEnderChest());
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatUtil.translate("&cPlayer '" + args[0] + "' not found."));
            return;
        }

        player.openInventory(target.getEnderChest());
    }
}
