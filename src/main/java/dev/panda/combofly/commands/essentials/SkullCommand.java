package dev.panda.combofly.commands.essentials;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand extends BaseCommand {

    @Command(name = "skull", permission = "pandafly.skull")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (args.length < 1) {
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player>"));
            return;
        }
        player.getInventory().addItem(this.getSkull(args[0]));
    }

    private ItemStack getSkull(String arg) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(arg);
        meta.setDisplayName(ChatUtil.translate("&fSkull of &c" + arg));
        skull.setItemMeta(meta);
        return skull;
    }
}
