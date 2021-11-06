package dev.panda.combofly.profile.commands.balance;

import dev.panda.combofly.ComboFly;
import dev.panda.chat.ChatUtil;
import dev.panda.combofly.utilities.Ints;
import dev.panda.command.BaseCommand;
import dev.panda.command.Command;
import dev.panda.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BalanceCommand extends BaseCommand {

    @Command(name = "balance", aliases = "bal")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();
        if (args.length < 1) {
            if (!player.hasPermission("pandafly.balance")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            String playerBalance = String.valueOf(ComboFly.get().getBalanceType().getBalance(player));

            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.PLAYER-BALANCE")
                    .replace("{player}", player.getName())
                    .replace("{balance}", playerBalance)));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (args.length < 2) {
            if (!player.hasPermission("pandafly.balance.others")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            if (target == null) {
                player.sendMessage(ChatUtil.translate("&cPlayer not found."));
                return;
            }

            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.TARGET-BALANCE")
                    .replace("{target}", target.getName())
                    .replace("{balance}", String.valueOf(ComboFly.get().getBalanceType().getBalance(target)))));
            return;
        }

        if (args[1].equalsIgnoreCase("set")) {
            if (!player.hasPermission("pandafly.balance.set")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            if (args.length < 3) {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player> <set|give|remove> <amount>"));
                return;
            }

            if (target == null) {
                player.sendMessage(ChatUtil.translate("&cPlayer not found."));
                return;
            }

            Integer amount = Ints.tryParse(args[2]);

            if (amount == null) {
                player.sendMessage(ChatUtil.translate("&cAmount must be a number."));
                return;
            }

            if (amount < 0) {
                player.sendMessage(ChatUtil.translate("&cAmount must be positive."));
                return;
            }

            ComboFly.get().getBalanceType().setBalance(target, amount);
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.SET-BALANCE")
                    .replace("{target}", target.getName())
                    .replace("{amount}", String.valueOf(amount))));
        }
        else if (args[1].equalsIgnoreCase("give")) {
            if (!player.hasPermission("pandafly.balance.give")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            if (args.length < 3) {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player> <set|give|remove> <amount>"));
                return;
            }

            if (target == null) {
                player.sendMessage(ChatUtil.translate("&cPlayer not found."));
                return;
            }

            Integer amount = Ints.tryParse(args[2]);

            if (amount == null) {
                player.sendMessage(ChatUtil.translate("&cAmount must be a number."));
                return;
            }

            if (amount < 0) {
                player.sendMessage(ChatUtil.translate("&cAmount must be positive."));
                return;
            }

            int targetBalance = (int) ComboFly.get().getBalanceType().getBalance(target);

            ComboFly.get().getBalanceType().giveBalance(player, targetBalance + amount);
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.GIVE-BALANCE")
                    .replace("{target}", target.getName())
                    .replace("{amount}", String.valueOf(amount))));
        }
        else if (args[1].equalsIgnoreCase("remove")) {
            if (!player.hasPermission("pandafly.balance.remove")) {
                player.sendMessage(ChatUtil.translate("&cYou don't have permissions"));
                return;
            }

            if (args.length < 3) {
                player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <player> <set|give|remove> <amount>"));
                return;
            }

            if (target == null) {
                player.sendMessage(ChatUtil.translate("&cPlayer not found."));
                return;
            }

            Integer amount = Ints.tryParse(args[2]);

            if (amount == null) {
                player.sendMessage(ChatUtil.translate("&cAmount must be a number."));
                return;
            }

            if (amount < 0) {
                player.sendMessage(ChatUtil.translate("&cAmount must be positive."));
                return;
            }

            int targetBalance = (int) ComboFly.get().getBalanceType().getBalance(target);

            ComboFly.get().getBalanceType().removeBalance(player, targetBalance - amount);
            player.sendMessage(ChatUtil.translate(ComboFly.get().getMessageConfig().getString("BALANCE.REMOVE-BALANCE")
                    .replace("{target}", target.getName())
                    .replace("{amount}", String.valueOf(amount))));
        }
    }
}
