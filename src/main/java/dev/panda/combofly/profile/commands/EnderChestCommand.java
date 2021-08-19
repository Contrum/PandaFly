package dev.panda.combofly.profile.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EnderChestCommand extends BaseCommand {

    @Command(name = "enderchest", permission = "pandafly.enchant", aliases = {"ec", "echest"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        
        if (args.length < 1) {
            if (!player.hasPermission("pandafly.enderchest")){
                player.sendMessage(CC.getNoPermission());
                return;
            }

            if (ComboFly.get().getCombatManager().hasCooldown(player)) {
                player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPAWN.COMBAT")));
                return;
            }

            player.openInventory(player.getEnderChest());
            return;
        }

        if (ComboFly.get().getCombatManager().hasCooldown(player)) {
            player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("SPAWN.COMBAT")));
            return;
        }

        if (!player.hasPermission("pandafly.enderchest.others")) {
            player.openInventory(player.getEnderChest());
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(CC.getPlayerNotFound(args[0]));
            return;
        }

        player.openInventory(target.getEnderChest());
    }
}
