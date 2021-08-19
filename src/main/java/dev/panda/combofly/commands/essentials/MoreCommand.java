package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.utilities.CC;
import dev.panda.combofly.utilities.Ints;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoreCommand extends BaseCommand {

    @Command(name = "more", permission = "pandafly.more")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /" + label + " <amount>"));
            return;
        }

        Integer amount = Ints.tryParse(args[0]);

        if (amount == null) {
            player.sendMessage(CC.translate("&cAmount must be a number."));
            return;
        }

        if (amount <= 0) {
            player.sendMessage(CC.translate("&cAmount must be positive."));
            return;
        }

        ItemStack is = player.getItemInHand();

        if (is == null || is.getType().equals(Material.AIR)) {
            player.sendMessage(CC.translate("&cYou need hold an item."));
            return;
        }

        if (is.getAmount() >= 64) {
            player.sendMessage(CC.translate("&cYou already have x64."));
            return;
        }

        this.moreItem(player, is, amount);
    }

    private void moreItem(Player player, ItemStack is, int amount) {
        is.setAmount(amount);
        player.setItemInHand(is);
        player.updateInventory();
    }
}
