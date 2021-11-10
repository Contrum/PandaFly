package dev.panda.combofly.kit.commands;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.Command;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.entity.Player;

public class KitCommand extends BaseCommand {

    @Command(name = "kit", permission = "pandafly.kit")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        
        if (args.length < 1) {
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <kitName>"));
            player.sendMessage(ChatUtil.translate("&cAvailable Kits: " +
                    ComboFly.get().getKitManager().getKits()));
            return;
        }

        String kitName = ChatUtil.capitalize(args[0]);

        if (ComboFly.get().getKitConfig().getString("KITS." + kitName) == null) {
            player.sendMessage(ChatUtil.translate("&cKit '" + kitName + "' not found."));
            player.sendMessage(ChatUtil.translate("&cAvailable Kits: " +
                    ComboFly.get().getKitManager().getKits()));
            return;

        }

        if (ComboFly.get().getCombatManager().hasCooldown(player)) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("KIT.COMBAT")));
            return;
        }

        ComboFly.get().getKitManager().giveKit(player, kitName);
    }
}
