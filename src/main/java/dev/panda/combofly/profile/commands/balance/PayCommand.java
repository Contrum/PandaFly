package dev.panda.combofly.profile.commands.balance;

import dev.panda.combofly.ComboFly;
import dev.panda.lib.chat.ChatUtil;
import dev.panda.combofly.utilities.Ints;
import dev.panda.lib.command.BaseCommand;
import dev.panda.lib.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PayCommand extends BaseCommand {

    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (!player.hasPermission("pandafly.pay")) {
            player.hasPermission(ChatUtil.translate("&cYou don't have permission."));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player> <amount>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatUtil.translate("&cPlayer not found."));
            return;
        }

        if (player.equals(target)) {
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.PAY-SELF")));
            return;
        }

        Integer amount = Ints.tryParse(args[1]);

        if (amount == null) {
            player.sendMessage(ChatUtil.translate("&cAmount must be a number."));
            return;
        }

        if (amount <= 0) {
            player.sendMessage(ChatUtil.translate("&cAmount must be positive."));
            return;
        }

        int playerBalance = (int) ComboFly.get().getBalanceType().getBalance(player);
        int targetBalance = (int) ComboFly.get().getBalanceType().getBalance(target);

        if (playerBalance < amount) {
            ComboFly.get().getMainConfig().getStringList("PAY-ENOUGH-BALANCE").forEach(key -> player.sendMessage(key
                    .replace("{amount}", String.valueOf(playerBalance))));
            return;
        }

        ComboFly.get().getBalanceType().setBalance(player, playerBalance - amount);
        ComboFly.get().getBalanceType().setBalance(target, targetBalance + amount);
        player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.PAY-SEND")
                .replace("{amount}", String.valueOf(amount))
                .replace("{target}", target.getName())));
        target.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.PAY-RECEIVE")
                .replace("{amount}", String.valueOf(amount))
                .replace("{player}", player.getName())));
    }
}
