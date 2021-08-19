package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.combofly.utilities.CC;
import dev.risas.panda.command.BaseCommand;
import dev.risas.panda.command.Command;
import dev.risas.panda.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand extends BaseCommand {
    
    @Command(name = "rename", permission = "pandafly.rename")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /" + label + " <name>"));
            return;
        }

        ItemStack is = player.getItemInHand();

        if (is == null || is.getType().equals(Material.AIR)) {
            player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("RENAME.NO-ITEM")));
            return;
        }

        StringBuilder name = new StringBuilder();
        ItemMeta meta = is.getItemMeta();

        for (int i = 0; i != args.length; i++) {
            name.append(args[i]).append(" ");
        }

        meta.setDisplayName(CC.translate(name.toString()));
        is.setItemMeta(meta);

        player.setItemInHand(is);
        player.sendMessage(CC.translate(ComboFly.get().getMessageConfig().getString("RENAME.RENAMED")
                .replace("{rename}", name.toString())));
    }
}
